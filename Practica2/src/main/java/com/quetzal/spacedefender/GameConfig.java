package com.quetzal.spacedefender;

/**
 * Valores de jugabilidad no definidos explícitamente en el enunciado.
 * Se concentran aquí para que puedan ajustarse fácilmente si el auxiliar
 * establece números diferentes durante la calificación.
 */
public final class GameConfig {
    private GameConfig() {
    }

    public static final int INITIAL_LIVES = 3;
    public static final int GAME_DURATION_SECONDS = 60;
    public static final int ENEMY_SCORE = 20;
    public static final int MIN_SPAWN_DELAY_MS = 700;
    public static final int EXTRA_RANDOM_SPAWN_DELAY_MS = 900;

    public static int enemySpeed(Difficulty difficulty) {
        return switch (difficulty) {
            case FACIL -> 3;
            case NORMAL -> 4;
            case DIFICIL -> 5;
        };
    }
}
