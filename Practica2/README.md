# Quetzal Space Defender - Side Scroller

Práctica 2 de Introducción a la Programación y Computación 1.

## Requisitos
- Java JDK 17 o superior
- Maven 3.8+
- Internet la primera vez para descargar JFreeChart

## Ejecutar
```bash
mvn clean compile
mvn exec:java
```

También puede abrirse como proyecto Maven en NetBeans o IntelliJ IDEA.

## Funcionalidades
- Menú principal: Jugar, Crear Piloto, Top de Puntajes, Historial, Salir.
- Pilotos almacenados en `Vector`.
- Tres dificultades: Fácil, Normal y Difícil.
- Side Scroller horizontal.
- Cada enemigo y cada proyectil se ejecuta con un hilo independiente.
- Snitch espacial: +150 puntos y elimina enemigos visibles.
- Quaffle: +10 puntos.
- Bludger: bloquea el movimiento del jugador durante 2 segundos.
- Historial de partidas en memoria usando `Vector`.
- Top de puntajes con gráfica JFreeChart.
- Exportación de reporte HTML con `java.io.FileWriter` y `PrintWriter`.

## Valores configurables no definidos por el PDF
La duración de la partida, vidas iniciales, puntaje por enemigo y frecuencia de aparición se centralizan en `GameConfig.java`. Estos valores son decisiones de implementación y pueden cambiarse sin alterar la arquitectura.
