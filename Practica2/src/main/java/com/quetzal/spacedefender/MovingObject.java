package com.quetzal.spacedefender;

import java.awt.*;

public class MovingObject {
    private int x;
    private int y;
    private final int width;
    private final int height;
    private final GameObjectType type;
    private volatile boolean active = true;

    public MovingObject(int x, int y, int width, int height, GameObjectType type) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
    }

    public synchronized void moveLeft(int amount) {
        x -= amount;
    }

    public synchronized int getX() {
        return x;
    }

    public synchronized int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public GameObjectType getType() {
        return type;
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
