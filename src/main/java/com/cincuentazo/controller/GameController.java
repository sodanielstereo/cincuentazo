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
    private VBox machine1Panel;

    @FXML
    private VBox machine2Panel;

    @FXML
    private VBox machine3Panel;

    @FXML
    private Label machine1TitleLabel;

    @FXML
    private Label machine2TitleLabel;

    @FXML
    private Label machine3TitleLabel;

    @FXML
    private Label machine1StatusLabel;

    @FXML
    private Label machine2StatusLabel;

    @FXML
    private Label machine3StatusLabel;

    @FXML
    private HBox machine1HandBox;

    @FXML
    private HBox machine2HandBox;

    @FXML
    private HBox machine3HandBox;

    @FXML
    private HBox humanHandBox;

    @FXML
    private Label humanTitleLabel;

    @FXML
    private Button drawButton;

    @FXML
    private TextArea logArea;

    private Game game;
    private boolean artificialTurnRunning;

    /**
     * Inicia una nueva partida.
     *
     * @param realPlayerName nombre del jugador real
     * @param artificialPlayers cantidad de jugadores artificiales
     */
    public void startNewGame(String realPlayerName, int artificialPlayers) {
        try {
            game = new Game(realPlayerName, artificialPlayers);
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
        updateArtificialPlayers();
        updateHumanHandBox();
        updateLogArea();
    }

    /**
     * Actualiza la información general del juego.
     */
    private void updateGeneralInfo() {
        Card topCard = game.getTable().getTopCard();

        tableSumLabel.setText("Suma: " + game.getTable().getCurrentSum());
        tableCardLabel.setText(topCard == null ? "-" : topCard.toString());
        deckSizeLabel.setText("Mazo: " + game.getDeck().size());

        tableCardLabel.getStyleClass().removeAll("red-card", "black-card");

        if (topCard != null) {
            if (isRedCard(topCard)) {
                tableCardLabel.getStyleClass().add("red-card");
            } else {
                tableCardLabel.getStyleClass().add("black-card");
            }
        }

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
     * Actualiza los paneles de los jugadores artificiales.
     */
    private void updateArtificialPlayers() {
        updateArtificialPlayerPanel(1, machine1Panel, machine1TitleLabel, machine1StatusLabel, machine1HandBox);
        updateArtificialPlayerPanel(2, machine2Panel, machine2TitleLabel, machine2StatusLabel, machine2HandBox);
        updateArtificialPlayerPanel(3, machine3Panel, machine3TitleLabel, machine3StatusLabel, machine3HandBox);
    }

    /**
     * Actualiza un panel específico de jugador artificial.
     *
     * @param playerIndex índice del jugador en la lista del modelo
     * @param panel panel visual
     * @param titleLabel etiqueta de título
     * @param statusLabel etiqueta de estado
     * @param handBox contenedor de cartas boca abajo
     */
    private void updateArtificialPlayerPanel(
            int playerIndex,
            VBox panel,
            Label titleLabel,
            Label statusLabel,
            HBox handBox
    ) {
        if (playerIndex >= game.getPlayers().size()) {
            panel.setVisible(false);
            panel.setManaged(false);
            return;
        }

        panel.setVisible(true);
        panel.setManaged(true);

        Player player = game.getPlayers().get(playerIndex);

        titleLabel.setText("[ " + player.getName() + " 🤖 ]");
        statusLabel.setText(player.isActive() ? "Activo - Cartas: " + player.getHand().size() : "Eliminado");

        panel.getStyleClass().removeAll("machine-panel-current", "machine-panel-eliminated");

        if (player == game.getCurrentPlayer() && player.isActive()) {
            panel.getStyleClass().add("machine-panel-current");
        }

        if (!player.isActive()) {
            panel.getStyleClass().add("machine-panel-eliminated");
        }

        handBox.getChildren().clear();

        int cardsToShow = player.getHand().size();

        for (int i = 0; i < cardsToShow; i++) {
            Label cardBack = new Label("◆");
            cardBack.getStyleClass().add("card-back");
            handBox.getChildren().add(cardBack);
        }
    }

    /**
     * Actualiza las cartas visibles del jugador real.
     */
    private void updateHumanHandBox() {
        humanHandBox.getChildren().clear();

        Player realPlayer = game.getPlayers().get(0);
        humanTitleLabel.setText("[ " + realPlayer.getName() + " ]");
        List<Card> hand = realPlayer.getHand();

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            Button cardButton = new Button(card.toString());

            int cardIndex = i;
            cardButton.setOnAction(event -> playRealPlayerCard(cardIndex));

            cardButton.getStyleClass().add("card-button");

            if (isRedCard(card)) {
                cardButton.getStyleClass().add("red-card");
            } else {
                cardButton.getStyleClass().add("black-card");
            }

            boolean canPlayNow = !artificialTurnRunning
                    && game.getState() == GameState.WAITING_REAL_PLAYER_CARD
                    && !game.getCurrentPlayer().isArtificial()
                    && realPlayer.isActive()
                    && card.canBePlayed(game.getTable().getCurrentSum());

            cardButton.setDisable(!canPlayNow);

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
     * Determina si una carta debe mostrarse en color rojo.
     *
     * @param card carta a evaluar
     * @return true si es corazón o diamante
     */
    private boolean isRedCard(Card card) {
        return card.getSuit().name().equals("HEARTS")
                || card.getSuit().name().equals("DIAMONDS");
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