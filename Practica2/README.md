# Quetzal Space Defender - Side Scroller

## Descripción

**Quetzal Space Defender** es una aplicación desarrollada en Java para la Práctica 2 del curso **Introducción a la Programación y Computación 1**.

El proyecto consiste en un juego tipo **Side Scroller** con desplazamiento horizontal, creación de pilotos, selección de dificultad, enemigos, objetos especiales, disparos, sistema de puntajes, historial de partidas, gráfica de mejores puntajes y exportación de reportes.

---

## Tecnologías utilizadas

- Java
- Java Swing
- Programación Orientada a Objetos
- Hilos (`Thread`)
- Vectores (`Vector`)
- JFreeChart
- Maven
- `java.io.*`
- HTML/CSS para reportes

---

## Funcionalidades principales

- Crear pilotos con validación de nombres.
- Seleccionar una dificultad y modelo de nave.
- Jugar una partida tipo Side Scroller.
- Movimiento de la nave con teclado.
- Disparos mediante hilos independientes.
- Enemigos y objetos móviles mediante hilos independientes.
- Sistema de vidas.
- Sistema de puntaje.
- Historial de partidas.
- Top de puntajes.
- Gráfica de mejores puntajes con JFreeChart.
- Exportación de historial a un reporte HTML.
- Documentación técnica y de usuario.

---

## Dificultades

| Dificultad | Nave | Característica principal | Cadencia de disparo |
|---|---|---|---|
| Fácil | Explorador | Alta velocidad / disparo lento | 2 segundos |
| Normal | Caza Estelar | Velocidad y disparo equilibrados | 1 segundo |
| Difícil | Acorazado | Menor velocidad / disparo rápido | 0.3 segundos |

---

## Objetos del juego

| Objeto | Efecto |
|---|---|
| Enemigo | Reduce una vida si colisiona con la nave. |
| Snitch | Otorga 150 puntos y elimina los enemigos visibles. |
| Bludger | Bloquea temporalmente el movimiento de la nave durante 2 segundos. |
| Quaffle | Suma 10 puntos al puntaje actual. |

---

## Controles

| Acción | Control |
|---|---|
| Mover arriba | `W` o flecha ↑ |
| Mover abajo | `S` o flecha ↓ |
| Mover izquierda | `A` o flecha ← |
| Mover derecha | `D` o flecha → |
| Disparar | Barra espaciadora |
| Terminar partida | `ESC` |

---

## Estructura del proyecto

```text
Practica2/
├── Documento PDF/
│   ├── DIAGRAMA_FLUJO_Quetzal_Space_Defender.pdf
│   ├── MANUAL_TECNICO_Quetzal_Space_Defender.pdf
│   └── MANUAL_USUARIO_Quetzal_Space_Defender.pdf
│
├── HTML/
│   └── reporte_quetzal.html
│
├── docs/
│   ├── DIAGRAMA_FLUJO.md
│   ├── MANUAL_TECNICO.md
│   └── MANUAL_USUARIO.md
│
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── quetzal/
│                   └── spacedefender/
│                       ├── AppData.java
│                       ├── Difficulty.java
│                       ├── GameConfig.java
│                       ├── GameFrame.java
│                       ├── GameObjectType.java
│                       ├── GamePanel.java
│                       ├── GameRecord.java
│                       ├── HistoryFrame.java
│                       ├── Main.java
│                       ├── MainMenuFrame.java
│                       ├── MovingObject.java
│                       ├── Pilot.java
│                       ├── PilotFrame.java
│                       ├── Projectile.java
│                       ├── ReportExporter.java
│                       └── TopScoresFrame.java
│
├── pom.xml
└── README.md
```

---

## Descripción de clases principales

### `Main.java`
Punto de entrada de la aplicación.

### `AppData.java`
Administra los pilotos y el historial de partidas mediante vectores.

### `Pilot.java`
Representa la información de un piloto.

### `Difficulty.java`
Define las dificultades, los modelos de nave, velocidades y tiempos de disparo.

### `MainMenuFrame.java`
Contiene el menú principal de la aplicación.

### `PilotFrame.java`
Permite crear pilotos y validar nombres duplicados.

### `GameFrame.java` y `GamePanel.java`
Contienen la lógica principal y la interfaz de la partida.

### `MovingObject.java`
Representa enemigos y objetos especiales.

### `Projectile.java`
Representa los disparos del jugador.

### `HistoryFrame.java`
Muestra el historial de partidas.

### `TopScoresFrame.java`
Genera la gráfica de mejores puntajes mediante JFreeChart.

### `ReportExporter.java`
Genera el reporte HTML usando `FileWriter` y `PrintWriter`.

---

## Uso de hilos

El proyecto utiliza hilos para controlar el movimiento independiente de elementos del juego.

- Cada enemigo u objeto móvil se ejecuta en su propio hilo.
- Cada proyectil disparado por el jugador se ejecuta en un hilo independiente.
- El efecto del Bludger utiliza control temporal para bloquear el movimiento durante 2 segundos.

---

## Cómo ejecutar el proyecto

### Opción 1: Desde VS Code, NetBeans o IntelliJ IDEA

1. Abrir la carpeta `Practica2`.
2. Esperar a que el IDE cargue el proyecto Java/Maven.
3. Abrir:

```text
src/main/java/com/quetzal/spacedefender/Main.java
```

4. Ejecutar la clase `Main`.

### Opción 2: Utilizando Maven

Si Maven está instalado y configurado en el sistema:

```bash
mvn clean compile
```

Luego:

```bash
mvn exec:java
```

---

## Flujo básico de uso

1. Ejecutar la aplicación.
2. Seleccionar **Crear Piloto**.
3. Ingresar un nombre.
4. Seleccionar una dificultad.
5. Guardar el piloto.
6. Seleccionar **Jugar**.
7. Elegir un piloto.
8. Jugar utilizando los controles indicados.
9. Al finalizar, consultar el **Historial** o el **Top de Puntajes**.
10. Desde Historial se puede generar un reporte HTML.

---

## Reportes

El sistema permite exportar un reporte HTML desde la sección **Historial**.

El reporte contiene:

- Fecha de la partida.
- Nombre del piloto.
- Dificultad.
- Puntaje.
- Top de mejores puntajes.

El archivo es generado utilizando clases de `java.io.*`.

---

## Documentación

El repositorio incluye:

- Manual Técnico.
- Manual de Usuario.
- Diagrama de Flujo.
- Versiones en Markdown.
- Versiones PDF.
- Reporte HTML de ejemplo.

---

## Pruebas realizadas

Durante las pruebas se verificó:

- Creación de pilotos.
- Validación de pilotos duplicados.
- Selección de dificultad Fácil.
- Selección de dificultad Normal.
- Selección de dificultad Difícil.
- Movimiento de la nave.
- Disparos.
- Aparición de enemigos.
- Aparición de Quaffle.
- Aparición y efecto de Bludger.
- Aparición de Snitch.
- Sistema de puntajes.
- Finalización de partidas.
- Registro en historial.
- Gráfica de mejores puntajes.
- Exportación correcta del reporte HTML.

---

## Repositorio

Proyecto correspondiente a:

**Práctica 2 - Quetzal Space Defender - Side Scroller**

Curso:

**Introducción a la Programación y Computación 1**

Universidad de San Carlos de Guatemala  
Facultad de Ingeniería  
Escuela de Ingeniería en Ciencias y Sistemas
