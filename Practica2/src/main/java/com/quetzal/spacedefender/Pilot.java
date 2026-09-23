package com.quetzal.spacedefender;

public class Pilot {
    private final String name;
    private final Difficulty difficulty;

    public Pilot(String name, Difficulty difficulty) {
        this.name = name;
        this.difficulty = difficulty;
    }

    public String getName() {
        return name;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    @Override
    public String toString() {
        return name + " | " + difficulty;
    }
}
