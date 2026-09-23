package com.quetzal.spacedefender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

public final class ReportExporter {
    private ReportExporter() {
    }

    public static void exportHistory(AppData data, File file) throws IOException {
        Vector<GameRecord> history = data.getHistoryCopy();
        Vector<GameRecord> top = data.getSortedHistoryByScore();

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");
            out.println("<head>");
            out.println("<meta charset='UTF-8'>");
            out.println("<title>Reporte Quetzal Space Defender</title>");
            out.println("<style>");
            out.println("body{font-family:Arial,sans-serif;margin:40px;background:#f5f7fb;color:#1b2430;}");
            out.println("h1,h2{color:#174ea6;}");
            out.println("table{border-collapse:collapse;width:100%;background:white;}");
            out.println("th,td{border:1px solid #bbb;padding:8px;text-align:left;}");
            out.println("th{background:#e9eef8;}");
            out.println(".card{background:white;padding:20px;border-radius:10px;margin-bottom:25px;}");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");

            out.println("<h1>Quetzal Space Defender</h1>");
            out.println("<p>Reporte de historial y mejores puntajes.</p>");

            out.println("<div class='card'>");
            out.println("<h2>Historial</h2>");
            out.println("<table>");
            out.println("<tr><th>Fecha</th><th>Piloto</th><th>Dificultad</th><th>Puntaje</th></tr>");

            for (GameRecord r : history) {
                out.println("<tr>");
                out.println("<td>" + escape(r.getFormattedDate()) + "</td>");
                out.println("<td>" + escape(r.getPilotName()) + "</td>");
                out.println("<td>" + escape(r.getDifficulty().toString()) + "</td>");
                out.println("<td>" + r.getScore() + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</div>");

            out.println("<div class='card'>");
            out.println("<h2>Top de Puntajes</h2>");
            out.println("<ol>");

            int limit = Math.min(10, top.size());
            for (int i = 0; i < limit; i++) {
                GameRecord r = top.get(i);
                out.println("<li>"
                        + escape(r.getPilotName())
                        + " - "
                        + r.getScore()
                        + " puntos</li>");
            }

            out.println("</ol>");
            out.println("</div>");

            out.println("</body>");
            out.println("</html>");
        }
    }

    private static String escape(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
