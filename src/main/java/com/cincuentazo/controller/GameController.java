package com.cincuentazo.controller;

import java.io.IOException;
import java.util.List;

import com.cincuentazo.App;
import com.cincuentazo.exceptions.EmptyDeckException;
import com.cincuentazo.exceptions.GameConfigurationException;
import com.cincuentazo.exceptions.InvalidMoveException;
import com.cincuentazo.model.Card;
import com.cincuentazo.model.Game;
import com.cincuentazo.model.GameState;
import com.cincuentazo.model.Player;
import com.cincuentazo.threads.ArtificialDrawThread;
import com.cincuentazo.threads.ArtificialPlayThread;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Controlador de la pantalla principal del juego.
 * Conecta la interfaz JavaFX con la lógica del modelo Game.
 */
public class GameController {

    @FXML
    private Label tableSumLabel;

    @FXML
    private Label tableCardLabel;

    @FXML
    private Label deckSizeLabel;

    @FXML
    private Label currentTurnLabel;

    @FXML
    private Label stateLabel;

    @FXML
    private Label winnerLabel;

    @FXML
    private VBox playersBox;

    @FXML
    private HBox humanHandBox;

    @FXML
    private Button drawButton;

    @FXML
    private TextArea logArea;

    private Game game;
    private boolean artificialTurnRunning;

    /**
     * Inicia una nueva partida.
     *
     * @param artificialPlayers cantidad de jugadores artificiales
     */
    public void startNewGame(int artificialPlayers) {
        try {
            game = new Game(artificialPlayers);
            artificialTurnRunning = false;

            refreshView();
            executeArtificialTurnsIfNeeded();

        } catch (GameConfigurationException | EmptyDeckException exception) {
            App.showError("No se pudo iniciar el juego", exception.getMessage());
        }
    }

    /**
     * Maneja el botón de tomar carta del mazo.
     */
    @FXML
    private void onDrawCard() {
        if (game == null || artificialTurnRunning) {
            return;
        }

        try {
            game.drawForRealPlayer();

            refreshView();
            executeArtificialTurnsIfNeeded();

        } catch (EmptyDeckException | InvalidMoveException exception) {
            App.showError("Movimiento inválido", exception.getMessage());
            refreshView();
        }
    }

    /**
     * Regresa a la pantalla inicial.
     */
    @FXML
    private void onBackToStart() {
        try {
            App.showStartView();
        } catch (IOException exception) {
            App.showError("Error", "No se pudo volver a la pantalla inicial.");
        }
    }

    /**
     * Juega una carta del jugador real.
     *
     * @param cardIndex índice de la carta seleccionada
     */
    private void playRealPlayerCard(int cardIndex) {
        if (game == null || artificialTurnRunning) {
            return;
        }

        try {
            game.playRealPlayerCard(cardIndex);
            refreshView();

        } catch (InvalidMoveException exception) {
            App.showError("Carta inválida", exception.getMessage());
            refreshView();
        }
    }

    /**
     * Ejecuta el turno artificial usando hilos.
     * Si el jugador actual es artificial, espera entre 2 y 4 segundos
     * antes de jugar una carta.
     */
    private void executeArtificialTurnsIfNeeded() {
        if (game == null || artificialTurnRunning || game.getState() == GameState.FINISHED) {
            return;
        }

        if (!game.getCurrentPlayer().isArtificial()) {
            return;
        }

        if (game.getState() == GameState.ARTIFICIAL_PLAYER_TURN) {
            artificialTurnRunning = true;
            refreshView();

            ArtificialPlayThread playThread = new ArtificialPlayThread(this::executeArtificialPlay);
            playThread.setDaemon(true);
            playThread.start();
        }
    }

    /**
     * Ejecuta la jugada del jugador artificial actual.
     * Este método se ejecuta en el hilo de JavaFX después de la espera del hilo artificial.
     */
    private void executeArtificialPlay() {
        try {
            game.playCurrentArtificialCard();
            refreshView();

            if (game.getState() == GameState.ARTIFICIAL_PLAYER_DRAW) {
                ArtificialDrawThread drawThread = new ArtificialDrawThread(this::executeArtificialDraw);
                drawThread.setDaemon(true);
                drawThread.start();
                return;
            }

            artificialTurnRunning = false;
            refreshView();
            executeArtificialTurnsIfNeeded();

        } catch (InvalidMoveException exception) {
            artificialTurnRunning = false;
            App.showError("Error en turno artificial", exception.getMessage());
            refreshView();
        }
    }

    /**
     * Ejecuta la acción de tomar carta del jugador artificial actual.
     * Este método se ejecuta en el hilo de JavaFX después de la espera del hilo artificial.
     */
    private void executeArtificialDraw() {
        try {
            game.drawForCurrentArtificialPlayer();

            artificialTurnRunning = false;
            refreshView();
            executeArtificialTurnsIfNeeded();

        } catch (EmptyDeckException | InvalidMoveException exception) {
            artificialTurnRunning = false;
            App.showError("Error al tomar carta", exception.getMessage());
            refreshView();
        }
    }

    /**
     * Actualiza todos los elementos visibles de la interfaz.
     */
    private void refreshView() {
        if (game == null) {
            return;
        }

        updateGeneralInfo();
        updatePlayersBox();
        updateHumanHandBox();
        updateLogArea();
    }

    /**
     * Actualiza la información general del juego.
     */
    private void updateGeneralInfo() {
        Card topCard = game.getTable().getTopCard();

        tableSumLabel.setText("Suma: " + game.getTable().getCurrentSum());
        tableCardLabel.setText("Mesa: " + (topCard == null ? "-" : topCard.toString()));
        deckSizeLabel.setText("Mazo: " + game.getDeck().size());

        Player currentPlayer = game.getCurrentPlayer();
        currentTurnLabel.setText("Turno: " + currentPlayer.getName());

        if (artificialTurnRunning) {
            stateLabel.setText("Estado: " + translateState(game.getState()) + "...");
        } else {
            stateLabel.setText("Estado: " + translateState(game.getState()));
        }

        if (game.getState() == GameState.FINISHED) {
            winnerLabel.setText("Ganador: " + game.getWinnerName());
        } else {
            winnerLabel.setText("");
        }

        updateActionAvailability();
    }

    /**
     * Habilita o deshabilita las acciones del jugador real según el estado del juego.
     */
    private void updateActionAvailability() {
        drawButton.setDisable(
                artificialTurnRunning
                        || game.getState() != GameState.WAITING_REAL_PLAYER_DRAW
        );
    }

    /**
     * Actualiza la lista visual de jugadores.
     */
    private void updatePlayersBox() {
        playersBox.getChildren().clear();

        for (Player player : game.getPlayers()) {
            String status = player.isActive() ? "Activo" : "Eliminado";
            String type = player.isArtificial() ? "Artificial" : "Real";

            Label playerLabel = new Label(
                    player.getName()
                            + "\nTipo: " + type
                            + "\nEstado: " + status
                            + "\nCartas: " + player.getHand().size()
            );

            playerLabel.setStyle(
                    "-fx-text-fill: white;"
                            + "-fx-padding: 8;"
                            + "-fx-border-color: #596070;"
                            + "-fx-border-radius: 8;"
            );

            playersBox.getChildren().add(playerLabel);
        }
    }

    /**
     * Actualiza las cartas visibles del jugador real.
     */
    private void updateHumanHandBox() {
        humanHandBox.getChildren().clear();

        Player realPlayer = game.getPlayers().get(0);
        List<Card> hand = realPlayer.getHand();

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            Button cardButton = new Button(card.toString());

            int cardIndex = i;
            cardButton.setOnAction(event -> playRealPlayerCard(cardIndex));

            boolean canPlayNow = !artificialTurnRunning
                    && game.getState() == GameState.WAITING_REAL_PLAYER_CARD
                    && !game.getCurrentPlayer().isArtificial()
                    && realPlayer.isActive()
                    && card.canBePlayed(game.getTable().getCurrentSum());

            cardButton.setDisable(!canPlayNow);
            cardButton.setMinSize(75, 95);
            cardButton.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            humanHandBox.getChildren().add(cardButton);
        }
    }

    /**
     * Actualiza el área de registro de eventos.
     */
    private void updateLogArea() {
        logArea.clear();

        for (String message : game.getGameLog()) {
            logArea.appendText(message + "\n");
        }

        logArea.positionCaret(logArea.getText().length());
    }

    /**
     * Traduce el estado interno del juego a texto legible.
     *
     * @param state estado actual
     * @return texto legible
     */
    private String translateState(GameState state) {
        return switch (state) {
            case NOT_STARTED -> "No iniciado";
            case WAITING_REAL_PLAYER_CARD -> "Esperando carta del jugador";
            case WAITING_REAL_PLAYER_DRAW -> "Esperando que el jugador tome carta";
            case ARTIFICIAL_PLAYER_TURN -> "Turno de jugador artificial";
            case ARTIFICIAL_PLAYER_DRAW -> "Jugador artificial debe tomar carta";
            case FINISHED -> "Finalizado";
        };
    }
}