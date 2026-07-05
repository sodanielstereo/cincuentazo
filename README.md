# Cincuentazo

Cincuentazo es un juego de cartas desarrollado en JavaFX. El jugador real compite contra 1, 2 o 3 jugadores artificiales. El objetivo es sobrevivir jugando cartas sin hacer que la suma de la mesa supere 50.

## Integrantes

- Daniel Fernando Vallejo Cabrera 202343154
- Juan Pablo Lozano Restrepo 202521505

## Tecnologías utilizadas

- Java 17
- JavaFX 17.0.6
- Maven
- JUnit 5
- Git y GitHub

## Descripción del juego

En Cincuentazo, cada jugador inicia con una mano de 4 cartas. En la mesa hay una suma acumulada que no debe superar 50.

En cada turno, el jugador debe jugar una carta válida y luego tomar una carta del mazo. Si un jugador no tiene ninguna carta que pueda jugar sin superar 50, queda eliminado. Gana el último jugador activo.

## Reglas de las cartas

| Carta | Efecto |
|---|---|
| 2 al 8 | Suman su propio valor |
| 9 | Suma 0 |
| 10 | Suma 10 |
| J | Resta 10 |
| Q | Resta 10 |
| K | Resta 10 |
| As | Puede valer 1 o 10 según convenga |

## Funcionalidades principales

- Pantalla inicial con ícono del juego.
- Ingreso del nombre del jugador real.
- Selección de 1 a 3 jugadores artificiales.
- Botón de instrucciones con las reglas del juego.
- Interfaz gráfica estilo mesa de casino.
- Visualización de carta en mesa, suma actual, mazo y turno.
- Jugadores artificiales con cartas ocultas.
- Jugador real con cartas visibles.
- Registro de eventos de la partida.
- Eliminación automática de jugadores sin cartas válidas.
- Declaración automática del ganador.
- Uso de hilos para simular el tiempo de juego de los jugadores artificiales.
- Uso de excepciones personalizadas.
- Pruebas unitarias con JUnit.

Arquitectura

El proyecto utiliza una organización basada en MVC:

model: contiene la lógica del juego, cartas, mazo, jugadores, mesa, turnos y reglas.
controller: conecta la interfaz JavaFX con el modelo.
view: contiene los archivos FXML y estilos CSS.
exceptions: contiene las excepciones personalizadas del juego.
threads: contiene los hilos usados por los jugadores artificiales.
Excepciones implementadas
CincuentazoException
EmptyDeckException
GameConfigurationException
InvalidMoveException
Hilos implementados
ArtificialPlayThread: simula el tiempo de espera antes de que una máquina juegue una carta.
ArtificialDrawThread: simula el tiempo de espera antes de que una máquina tome una carta.
Pruebas unitarias

El proyecto incluye pruebas unitarias para:

CardTest
DeckTest
GameTest

Actualmente se ejecutan 16 pruebas unitarias.

## Estado del proyecto

El proyecto cumple con las funcionalidades principales solicitadas:

Juego funcional en JavaFX.
Interfaz gráfica.
Modelo del juego.
Jugadores artificiales.
Hilos.
Excepciones.
Pruebas unitarias.
Uso de Git y GitHub mediante ramas y pull requests.