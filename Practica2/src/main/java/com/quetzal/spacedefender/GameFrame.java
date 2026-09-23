package com.quetzal.spacedefender;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    public GameFrame(AppData data, Pilot pilot) {
        setTitle("Quetzal Space Defender - " + pilot.getName());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        GamePanel panel = new GamePanel(data, pilot, this);
        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                panel.stopGame();
            }
        });
    }
}
