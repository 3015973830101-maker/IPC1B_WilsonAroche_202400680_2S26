Manual Técnico

Proyecto
Quetzal Space Defender - Side Scroller

Tecnología
- Java
- Swing
- Hilos (`Thread`)
- `Vector`
- JFreeChart
- `java.io.*`

Estructura
- `Main`: punto de entrada.
- `AppData`: almacena pilotos e historial con vectores.
- `Pilot`: modelo de piloto.
- `Difficulty`: dificultad, nave, velocidad y cadencia.
- `MainMenuFrame`: menú principal.
- `PilotFrame`: creación de pilotos con validación.
- `GameFrame` / `GamePanel`: simulación principal.
- `MovingObject`: enemigos y objetos especiales.
- `Projectile`: proyectiles.
- `HistoryFrame`: historial.
- `TopScoresFrame`: gráfica de mejores puntajes con JFreeChart.
- `ReportExporter`: reporte HTML usando `FileWriter` y `PrintWriter`.

Hilos
Cada enemigo/objeto móvil se desplaza en su propio hilo y cada proyectil también se ejecuta en un hilo independiente.

Controles
- W / ↑: arriba
- S / ↓: abajo
- A / ←: izquierda
- D / →: derecha
- Espacio: disparar
- ESC: terminar partida
