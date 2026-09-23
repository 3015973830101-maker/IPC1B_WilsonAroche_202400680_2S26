package com.quetzal.spacedefender;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Vector;

public class TopScoresFrame extends JFrame {
    public TopScoresFrame(AppData data) {
        setTitle("Top de Puntajes");
        setSize(850, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Vector<GameRecord> sorted = data.getSortedHistoryByScore();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        int limit = Math.min(10, sorted.size());

        for (int i = 0; i < limit; i++) {
            GameRecord record = sorted.get(i);
            dataset.addValue(
                    record.getScore(),
                    "Puntaje",
                    record.getPilotName() + " #" + (i + 1)
            );
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Mejores Puntajes",
                "Piloto",
                "Puntos",
                dataset
        );

        JPanel root = new JPanel(new BorderLayout());

        if (sorted.isEmpty()) {
            root.add(
                    new JLabel("Todavía no hay partidas registradas.", SwingConstants.CENTER),
                    BorderLayout.CENTER
            );
        } else {
            root.add(new ChartPanel(chart), BorderLayout.CENTER);
        }

        add(root);
    }
}
