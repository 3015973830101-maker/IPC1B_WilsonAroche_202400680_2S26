package com.quetzal.spacedefender;

public enum Difficulty {
    FACIL("Explorador", 7, 2000),
    NORMAL("Caza Estelar", 5, 1000),
    DIFICIL("Acorazado", 3, 300);

    private final String shipName;
    private final int playerSpeed;
    private final int shotCooldownMs;

    Difficulty(String shipName, int playerSpeed, int shotCooldownMs) {
        this.shipName = shipName;
        this.playerSpeed = playerSpeed;
        this.shotCooldownMs = shotCooldownMs;
    }

    public String getShipName() {
        return shipName;
    }

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public int getShotCooldownMs() {
        return shotCooldownMs;
    }

    @Override
    public String toString() {
        String label = switch (this) {
            case FACIL -> "Fácil";
            case NORMAL -> "Normal";
            case DIFICIL -> "Difícil";
        };
        return label + " - " + shipName;
    }
}
