package com.quetzal.spacedefender;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameRecord {
    private final String pilotName;
    private final Difficulty difficulty;
    private final int score;
    private final LocalDateTime date;

    public GameRecord(String pilotName, Difficulty difficulty, int score) {
        this.pilotName = pilotName;
        this.difficulty = difficulty;
        this.score = score;
        this.date = LocalDateTime.now();
    }

    public String getPilotName() {
        return pilotName;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public int getScore() {
        return score;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    }

    @Override
    public String toString() {
        return getFormattedDate() + " | " + pilotName + " | " + difficulty + " | " + score + " pts";
    }
}
