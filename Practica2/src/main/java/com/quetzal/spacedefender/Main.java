package com.quetzal.spacedefender;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppData data = new AppData();
            new MainMenuFrame(data).setVisible(true);
        });
    }
}
