package com.quetzal.spacedefender;

import java.util.Vector;

public class AppData {
    private final Vector<Pilot> pilots = new Vector<>();
    private final Vector<GameRecord> history = new Vector<>();

    public synchronized boolean addPilot(Pilot pilot) {
        for (Pilot p : pilots) {
            if (p.getName().equalsIgnoreCase(pilot.getName())) {
                return false;
            }
        }
        pilots.add(pilot);
        return true;
    }

    public Vector<Pilot> getPilotsCopy() {
        return new Vector<>(pilots);
    }

    public synchronized void addGameRecord(GameRecord record) {
        history.add(record);
    }

    public Vector<GameRecord> getHistoryCopy() {
        return new Vector<>(history);
    }

    public Vector<GameRecord> getSortedHistoryByScore() {
        Vector<GameRecord> copy = getHistoryCopy();
        copy.sort((a, b) -> Integer.compare(b.getScore(), a.getScore()));
        return copy;
    }
}
