# Videojuego "Evasión Maritima" 

Este proyecto corresponde a un videojuego de evasión en 2D desarrollado en JAVA utulizando el framework LibGDX. En "Evasión Maritima", el jugador asume el control de una moto acuática con el objetivo de sobrevivir la mayor cantidad de tiempo posible esquivando barriles el cual le quitan vida y recolectando barriles de gasolina, con el fin de mantener el motor en marcha. El sistema gestiona físicas básicas de colisión, renderizado gráfico, reproducción de audio y una arquitectura orientada a objetos mediante patrones de diseño. 

## Caracteristicas Principales

* **Mecánica de supervivencia y evasión**: El jugador debe maniobrar ágilmente para esquivar obstáculos (barriles nornmales) que restan las vidas del chofer al impactar con él. 
* **Gestion de recursos**: Se incorpora un sistema de recolección de barriles de gasolina que caen por la pantalla siendo recursos vitales para que la moto no se quede sin combustible, y pueda seguir avanzando.
* **Sistema de puntuación y vidas**: Se monitorea en tiempo real el puntaje en cuanto al tiempo que lleva sobreviviendo y en cuanto a los objetos recolectados y las vidas restantes. 
* **Gestión de pantallas**: Se implementa un flujo juego completo el cual contiene una **pantalla de menú principal** y **pantalla de Game Over**.

##  Herramientas Utilizadas

* **Lenguaje**: Java 8 / 11.
* **Motor Gráfico/Framework**: LibGDX.
* **Gestión de Dependencias y construcción**: Gradle.
* **Control de Versiones**: Git/GitHub.
* **IDE**: Eclipse. 

## Arquitectura del Proyecto

El código sigue principios de **Programación Orientada a Objetos (POO)** y  buenas practicas de diseño de software:
- **Utilización de encapsulamiento y abstracción**: Uso de clases abstractas e interfaces para definir el comportamiento de las entidades del juego. 
- **Patrones de diseño**: El sistema integra diversos patrones de diseño para resolver problemas arquitectónicos comunes tal como **Singleton, Strategy, Template Method y Builder**. 

## Estructura del Proyecto 
Al utilizar LibGDX, el código está organizado en submódulos para separar la lógica central de la plataforma de ejecución:
* **`core/src/main/java/puppy/code`**: Contiene todo el motor y lógica del juego (independiente de la plataforma).
    * `GameEvasion`: Clase principal que administra el ciclo de vida del juego y el intercambio de pantallas.
    * `PantallaMenu` / `PantallaJuego` / `PantallaGameOver`: Clases que gestionan las distintas vistas y estados del juego.
    * `EntidadJuego` / `MotoAcuatica` / `Obstaculos`/ `Barril` / `BarrilGasolina`: Modelos de dominio que representan a los actores interactivos dentro de la pantalla, manejando sus propias posiciones, texturas y rectángulos de colisión.
* **`lwjgl3 (desktop)`**: Contiene los launchers específicos para ejecutar el juego en entornos de escritorio (Windows, Mac, Linux).
* **`assets/`**: Carpeta de recursos multimedia. Contiene todas las texturas (.png), efectos de sonido (.wav, .ogg) y música de fondo (.mp3) utilizados en el juego.

## Controles del juego
Para asegurar una experiencia fluida, el juego se controla de manera intuitiva mediante el teclado:

* **Movimiento Izquierda**: Tecla Flecha Izquierda.
* **Movimiento Derecha**: Tecla Flecha Derecha.
* **Comenzar Juego / Reiniciar Juego**: Tecla ENTER.  
* **Pausa Juego en Curso / Volver de PAUSA**: Tecla letra P.
* **Volver Menu Principal**: Tecla ESC (Escape). 

## Posibles Mejoras y Trabajo Futuro 

Aunque el juego base es completamente funcional, se han identificado las siguientes áreas de oportunidad para futuras versiones:

* **Progresión de dificultad**: Implementar un algoritmo que aumente la velocidad de caída de los barriles o la cantidad de obstáculos a medida que el jugador acumula más puntos.
* **Nuevos recursos:** Añadir elementos recolectables temporales, como un "escudo de invulnerabilidad" o un "imán de gasolina".
* **Persistencia de puntajes**: Integrar lectura y escritura de un archivo de texto local para guardar el récord máximo histórico de los jugadores con un registro de jugadores.
* **Cambios gráficos**: Integrar opciones de Skin para las motos de los personajes. 

##  Requisitos del Sistema y Ejecución

### Requisitos

* **Java Development Kit (JDK) 8** o superior (recomendado 11 o 17).
* Entorno de variables configurado correctamente para Java.
* Conexión a internet la primera vez que se ejecute para que Gradle descargue las dependencias de LibGDX.

### Ejecución 
#### Opción 1: Ejecución por TERMINAL 
1. Abre tu terminal de comandos y ubícate en la carpeta raíz del proyecto (donde se encuentra el archivo gradlew).
2. Ejecuta el siguiente comando para compilar y lanzar el juego directamente:
    * En Windows: `gradlew lwjgl3:run`.
    * En Mac/Linux: `./gradlew lwjgl3:run`

#### Opción 2: Ejecución mediante IDE ECLIPSE.

1. Abre Eclipse y asegúrate de tener el proyecto importado como un "Proyecto Gradle Existente".
2. Navega en el Package Explorer hasta el proyecto lanzador `(Juego-Progra-Avanzada-lwjgl3 o desktop).`
3. Busca el archivo `Lwjgl3Launcher.java` (o `DesktopLauncher.java`).
4. Haz clic derecho sobre el archivo, selecciona `Run As > Java Application`.


## Autores del programa 
* Manuel Moreno Galleguilos.
* Nicolas Echeverría Okroj 
* Hans Paz Bonilla. 
