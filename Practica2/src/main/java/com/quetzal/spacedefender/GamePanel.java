package com.quetzal.spacedefender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.Vector;

public class GamePanel extends JPanel implements KeyListener {
    public static final int WIDTH = 900;
    public static final int HEIGHT = 560;

    private final AppData data;
    private final Pilot pilot;
    private final JFrame owner;
    private final Random random = new Random();

    private final Vector<MovingObject> objects = new Vector<>();
    private final Vector<Projectile> projectiles = new Vector<>();

    private volatile boolean running = true;
    private volatile boolean movementBlocked = false;
    private volatile boolean gameFinished = false;

    private int playerX = 70;
    private int playerY = HEIGHT / 2;
    private final int playerWidth = 55;
    private final int playerHeight = 34;

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;

    private int score = 0;
    private long lastShot = 0L;
    private int lives = GameConfig.INITIAL_LIVES;
    private long startTime = System.currentTimeMillis();

    private final Thread gameLoopThread;
    private final Thread spawnerThread;

    public GamePanel(AppData data, Pilot pilot, JFrame owner) {
        this.data = data;
        this.pilot = pilot;
        this.owner = owner;

        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(5, 10, 25));
        setFocusable(true);
        addKeyListener(this);

        gameLoopThread = new Thread(this::gameLoop, "GameLoop");
        spawnerThread = new Thread(this::spawnLoop, "Spawner");

        gameLoopThread.start();
        spawnerThread.start();

        SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    private void gameLoop() {
        while (running) {
            updatePlayer();
            checkCollisions();
            cleanupObjects();
            repaint();

            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= GameConfig.GAME_DURATION_SECONDS * 1000L || lives <= 0) {
                finishGame();
                break;
            }

            sleep(16);
        }
    }

    private void spawnLoop() {
        while (running) {
            GameObjectType type = randomType();
            int y = 50 + random.nextInt(HEIGHT - 130);
            MovingObject object = new MovingObject(
                    WIDTH + 20,
                    y,
                    type == GameObjectType.SNITCH ? 26 : 38,
                    type == GameObjectType.SNITCH ? 26 : 38,
                    type
            );

            objects.add(object);
            startObjectThread(object);

            sleep(GameConfig.MIN_SPAWN_DELAY_MS + random.nextInt(GameConfig.EXTRA_RANDOM_SPAWN_DELAY_MS));
        }
    }

    private GameObjectType randomType() {
        int value = random.nextInt(100);
        if (value < 62) return GameObjectType.ENEMY;
        if (value < 72) return GameObjectType.SNITCH;
        if (value < 86) return GameObjectType.BLUDGER;
        return GameObjectType.QUAFFLE;
    }

    private void startObjectThread(MovingObject object) {
        Thread thread = new Thread(() -> {
            int speed = GameConfig.enemySpeed(pilot.getDifficulty());

            while (running && object.isActive()) {
                object.moveLeft(speed);

                if (object.getX() < -object.getWidth()) {
                    object.deactivate();
                    break;
                }

                repaint();
                sleep(22);
            }
        }, "Object-" + object.getType() + "-" + System.nanoTime());

        thread.start();
    }

    private void updatePlayer() {
        if (movementBlocked) {
            return;
        }

        int speed = pilot.getDifficulty().getPlayerSpeed();

        if (up) playerY -= speed;
        if (down) playerY += speed;
        if (left) playerX -= speed;
        if (right) playerX += speed;

        playerX = Math.max(0, Math.min(WIDTH - playerWidth, playerX));
        playerY = Math.max(40, Math.min(HEIGHT - playerHeight, playerY));
    }

    private void fire() {
        long now = System.currentTimeMillis();

        if (now - lastShot < pilot.getDifficulty().getShotCooldownMs()) {
            return;
        }

        lastShot = now;

        Projectile projectile = new Projectile(
                playerX + playerWidth,
                playerY + playerHeight / 2
        );

        projectiles.add(projectile);

        Thread thread = new Thread(() -> {
            while (running && projectile.isActive()) {
                projectile.moveRight(10);

                if (projectile.getX() > WIDTH) {
                    projectile.deactivate();
                    break;
                }

                checkProjectileCollision(projectile);
                repaint();
                sleep(16);
            }
        }, "Projectile-" + System.nanoTime());

        thread.start();
    }

    private void checkProjectileCollision(Projectile projectile) {
        if (!projectile.isActive()) return;

        synchronized (objects) {
            for (MovingObject object : objects) {
                if (!object.isActive()) continue;

                if (projectile.getBounds().intersects(object.getBounds())) {
                    projectile.deactivate();
                    object.deactivate();

                    switch (object.getType()) {
                        case ENEMY -> score += GameConfig.ENEMY_SCORE;
                        case SNITCH -> {
                            score += 150;
                            destroyAllEnemies();
                        }
                        case QUAFFLE -> score += 10;
                        case BLUDGER -> blockMovement();
                    }
                    break;
                }
            }
        }
    }

    private void checkCollisions() {
        Rectangle player = new Rectangle(playerX, playerY, playerWidth, playerHeight);

        synchronized (objects) {
            for (MovingObject object : objects) {
                if (!object.isActive()) continue;

                if (player.intersects(object.getBounds())) {
                    object.deactivate();

                    switch (object.getType()) {
                        case ENEMY -> lives--;
                        case SNITCH -> {
                            score += 150;
                            destroyAllEnemies();
                        }
                        case BLUDGER -> blockMovement();
                        case QUAFFLE -> score += 10;
                    }
                }
            }
        }
    }

    private void destroyAllEnemies() {
        synchronized (objects) {
            for (MovingObject object : objects) {
                if (object.getType() == GameObjectType.ENEMY) {
                    object.deactivate();
                }
            }
        }
    }

    private void blockMovement() {
        if (movementBlocked) return;

        movementBlocked = true;

        Thread thread = new Thread(() -> {
            sleep(2000);
            movementBlocked = false;
        }, "BludgerBlock");

        thread.start();
    }

    private void cleanupObjects() {
        objects.removeIf(o -> !o.isActive());
        projectiles.removeIf(p -> !p.isActive());
    }

    private void finishGame() {
        if (gameFinished) return;
        gameFinished = true;
        running = false;

        data.addGameRecord(new GameRecord(
                pilot.getName(),
                pilot.getDifficulty(),
                score
        ));

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                    owner,
                    "Partida finalizada\nPiloto: " + pilot.getName()
                            + "\nPuntaje: " + score,
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );
            owner.dispose();
        });
    }

    public void stopGame() {
        if (!gameFinished) {
            finishGame();
        } else {
            running = false;
        }
    }

    private void sleep(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        drawStars(g2);
        drawHud(g2);
        drawPlayer(g2);
        drawObjects(g2);
        drawProjectiles(g2);

        if (movementBlocked) {
            g2.setColor(Color.ORANGE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 18));
            g2.drawString("BLUDGER: movimiento bloqueado", 300, 55);
        }

        g2.dispose();
    }

    private void drawStars(Graphics2D g2) {
        g2.setColor(new Color(150, 160, 180));
        for (int i = 0; i < 90; i++) {
            int x = (i * 97) % WIDTH;
            int y = 40 + ((i * 53) % (HEIGHT - 40));
            g2.fillRect(x, y, 2, 2);
        }
    }

    private void drawHud(Graphics2D g2) {
        g2.setColor(new Color(25, 30, 50));
        g2.fillRect(0, 0, WIDTH, 40);

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 14));

        long remaining = Math.max(0, GameConfig.GAME_DURATION_SECONDS - ((System.currentTimeMillis() - startTime) / 1000));

        g2.drawString("Piloto: " + pilot.getName(), 15, 25);
        g2.drawString("Nave: " + pilot.getDifficulty().getShipName(), 190, 25);
        g2.drawString("Puntos: " + score, 420, 25);
        g2.drawString("Vidas: " + lives, 560, 25);
        g2.drawString("Tiempo: " + remaining + "s", 680, 25);
    }

    private void drawPlayer(Graphics2D g2) {
        int[] xs = {playerX, playerX, playerX + playerWidth};
        int[] ys = {playerY, playerY + playerHeight, playerY + playerHeight / 2};

        g2.setColor(new Color(70, 220, 150));
        g2.fillPolygon(xs, ys, 3);

        g2.setColor(Color.WHITE);
        g2.drawPolygon(xs, ys, 3);
    }

    private void drawObjects(Graphics2D g2) {
        synchronized (objects) {
            for (MovingObject object : objects) {
                if (!object.isActive()) continue;

                switch (object.getType()) {
                    case ENEMY -> {
                        g2.setColor(new Color(225, 70, 70));
                        g2.fillRect(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                        g2.setColor(Color.WHITE);
                        g2.drawString("E", object.getX() + 14, object.getY() + 24);
                    }
                    case SNITCH -> {
                        g2.setColor(new Color(255, 215, 0));
                        g2.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                        g2.setColor(Color.WHITE);
                        g2.drawString("S", object.getX() + 9, object.getY() + 18);
                    }
                    case BLUDGER -> {
                        g2.setColor(new Color(150, 85, 190));
                        g2.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                        g2.setColor(Color.WHITE);
                        g2.drawString("B", object.getX() + 14, object.getY() + 24);
                    }
                    case QUAFFLE -> {
                        g2.setColor(new Color(70, 140, 255));
                        g2.fillOval(object.getX(), object.getY(), object.getWidth(), object.getHeight());
                        g2.setColor(Color.WHITE);
                        g2.drawString("Q", object.getX() + 13, object.getY() + 24);
                    }
                }
            }
        }
    }

    private void drawProjectiles(Graphics2D g2) {
        g2.setColor(new Color(255, 240, 100));

        synchronized (projectiles) {
            for (Projectile projectile : projectiles) {
                if (!projectile.isActive()) continue;
                Rectangle r = projectile.getBounds();
                g2.fillRect(r.x, r.y, r.width, r.height);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = true;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = true;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = true;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = true;
            case KeyEvent.VK_SPACE -> fire();
            case KeyEvent.VK_ESCAPE -> finishGame();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W, KeyEvent.VK_UP -> up = false;
            case KeyEvent.VK_S, KeyEvent.VK_DOWN -> down = false;
            case KeyEvent.VK_A, KeyEvent.VK_LEFT -> left = false;
            case KeyEvent.VK_D, KeyEvent.VK_RIGHT -> right = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
