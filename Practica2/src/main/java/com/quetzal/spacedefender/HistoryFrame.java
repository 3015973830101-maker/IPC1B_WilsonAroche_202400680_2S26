package com.quetzal.spacedefender;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class HistoryFrame extends JFrame {
    public HistoryFrame(AppData data) {
        setTitle("Historial de Partidas");
        setSize(700, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Vector<GameRecord> history = data.getHistoryCopy();

        String[] columns = {"Fecha", "Piloto", "Dificultad", "Puntaje"};
        Object[][] rows = new Object[history.size()][4];

        for (int i = 0; i < history.size(); i++) {
            GameRecord r = history.get(i);
            rows[i][0] = r.getFormattedDate();
            rows[i][1] = r.getPilotName();
            rows[i][2] = r.getDifficulty().toString();
            rows[i][3] = r.getScore();
        }

        JTable table = new JTable(rows, columns);
        table.setEnabled(false);

        JButton export = new JButton("Exportar reporte HTML");
        export.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setSelectedFile(new java.io.File("reporte_quetzal.html"));

            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    ReportExporter.exportHistory(data, chooser.getSelectedFile());
                    JOptionPane.showMessageDialog(this, "Reporte generado correctamente.");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                            this,
                            "No se pudo generar el reporte:\n" + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(export, BorderLayout.SOUTH);
    }
}
