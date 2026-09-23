package com.quetzal.spacedefender;

import javax.swing.*;
import java.awt.*;

public class PilotFrame extends JFrame {
    public PilotFrame(AppData data) {
        setTitle("Crear Piloto");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JTextField nameField = new JTextField();
        JComboBox<Difficulty> difficultyCombo = new JComboBox<>(Difficulty.values());

        JButton save = new JButton("Guardar");
        JButton cancel = new JButton("Cancelar");

        panel.add(new JLabel("Nombre:"));
        panel.add(nameField);
        panel.add(new JLabel("Dificultad / Nave:"));
        panel.add(difficultyCombo);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(save);
        panel.add(cancel);

        save.addActionListener(e -> {
            String name = nameField.getText().trim();

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "El nombre es obligatorio.");
                return;
            }

            if (name.length() < 3) {
                JOptionPane.showMessageDialog(this, "El nombre debe tener al menos 3 caracteres.");
                return;
            }

            Difficulty difficulty = (Difficulty) difficultyCombo.getSelectedItem();
            boolean saved = data.addPilot(new Pilot(name, difficulty));

            if (!saved) {
                JOptionPane.showMessageDialog(
                        this,
                        "Ya existe un piloto con ese nombre.",
                        "Dato duplicado",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            JOptionPane.showMessageDialog(this, "Piloto creado correctamente.");
            dispose();
        });

        cancel.addActionListener(e -> dispose());

        add(panel);
    }
}
