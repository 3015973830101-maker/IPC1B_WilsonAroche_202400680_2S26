package com.quetzal.spacedefender;

import java.awt.*;

public class Projectile {
    private int x;
    private int y;
    private final int width = 16;
    private final int height = 6;
    private volatile boolean active = true;

    public Projectile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public synchronized void moveRight(int amount) {
        x += amount;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), width, height);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        active = false;
    }
}
