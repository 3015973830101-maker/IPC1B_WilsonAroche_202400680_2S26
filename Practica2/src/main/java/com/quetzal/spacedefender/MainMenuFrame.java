package com.quetzal.spacedefender;

import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private final AppData data;

    public MainMenuFrame(AppData data) {
        this.data = data;

        setTitle("Quetzal Space Defender");
        setSize(520, 440);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel root = new JPanel(new BorderLayout(10, 10));
        root.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));

        JLabel title = new JLabel("QUETZAL SPACE DEFENDER", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel subtitle = new JLabel("Side Scroller", SwingConstants.CENTER);
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 18));

        JPanel header = new JPanel(new GridLayout(2, 1));
        header.add(title);
        header.add(subtitle);

        JPanel buttons = new JPanel(new GridLayout(5, 1, 10, 10));

        JButton play = new JButton("Jugar");
        JButton createPilot = new JButton("Crear Piloto");
        JButton top = new JButton("Top de Puntajes");
        JButton history = new JButton("Historial");
        JButton exit = new JButton("Salir");

        play.addActionListener(e -> openGame());
        createPilot.addActionListener(e -> new PilotFrame(data).setVisible(true));
        top.addActionListener(e -> new TopScoresFrame(data).setVisible(true));
        history.addActionListener(e -> new HistoryFrame(data).setVisible(true));
        exit.addActionListener(e -> System.exit(0));

        buttons.add(play);
        buttons.add(createPilot);
        buttons.add(top);
        buttons.add(history);
        buttons.add(exit);

        root.add(header, BorderLayout.NORTH);
        root.add(buttons, BorderLayout.CENTER);

        add(root);
    }

    private void openGame() {
        VectorModel model = new VectorModel(data.getPilotsCopy());

        if (model.getSize() == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Primero debe crear al menos un piloto.",
                    "Sin pilotos",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        JComboBox<Pilot> combo = new JComboBox<>(model.toArray());
        int option = JOptionPane.showConfirmDialog(
                this,
                combo,
                "Seleccione el piloto",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (option == JOptionPane.OK_OPTION && combo.getSelectedItem() != null) {
            Pilot pilot = (Pilot) combo.getSelectedItem();
            new GameFrame(data, pilot).setVisible(true);
        }
    }

    private static class VectorModel {
        private final java.util.Vector<Pilot> pilots;

        VectorModel(java.util.Vector<Pilot> pilots) {
            this.pilots = pilots;
        }

        int getSize() {
            return pilots.size();
        }

        Pilot[] toArray() {
            return pilots.toArray(new Pilot[0]);
        }
    }
}
