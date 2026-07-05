package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.cincuentazo.exceptions.EmptyDeckException;
import com.cincuentazo.exceptions.GameConfigurationException;
import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Clase principal del modelo del juego Cincuentazo.
 * Controla jugadores, mazo, mesa, turnos, eliminación y ganador.
 */
public class Game {

    private static final int MIN_ARTIFICIAL_PLAYERS = 1;
    private static final int MAX_ARTIFICIAL_PLAYERS = 3;
    private static final int INITIAL_HAND_SIZE = 4;

    private final Deck deck;
    private final Table table;
    private final List<Player> players;
    private final List<String> gameLog;

    private int currentPlayerIndex;
    private GameState state;
    private String winnerName;

    /**
     * Crea una nueva partida de Cincuentazo.
     *
     * @param artificialPlayers cantidad de jugadores artificiales
     * @throws GameConfigurationException si la cantidad de jugadores artificiales no es válida
     * @throws EmptyDeckException si no hay cartas suficientes para preparar el juego
     */
    public Game(int artificialPlayers) throws GameConfigurationException, EmptyDeckException {
        validateArtificialPlayers(artificialPlayers);

        this.deck = new Deck();
        this.table = new Table();
        this.players = new ArrayList<>();
        this.gameLog = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.state = GameState.NOT_STARTED;
        this.winnerName = "";

        createPlayers(artificialPlayers);
        prepareGame();
    }

    private void validateArtificialPlayers(int artificialPlayers) throws GameConfigurationException {
        if (artificialPlayers < MIN_ARTIFICIAL_PLAYERS || artificialPlayers > MAX_ARTIFICIAL_PLAYERS) {
            throw new GameConfigurationException("Debes seleccionar entre 1 y 3 jugadores artificiales.");
        }
    }

    private void createPlayers(int artificialPlayers) {
        players.add(new RealPlayer("Jugador"));

        for (int i = 1; i <= artificialPlayers; i++) {
            players.add(new ArtificialPlayer("Máquina " + i, new SafeStrategy()));
        }
    }

    private void prepareGame() throws EmptyDeckException {
        for (int i = 0; i < INITIAL_HAND_SIZE; i++) {
            for (Player player : players) {
                player.addCard(drawCardWithRecycle());
            }
        }

        Card initialCard = drawCardWithRecycle();
        table.placeInitialCard(initialCard);

        addLog("Carta inicial en la mesa: " + initialCard + ". Suma inicial: " + table.getCurrentSum() + ".");
        prepareCurrentTurn();
    }

    public void playRealPlayerCard(int cardIndex) {
        if (state != GameState.WAITING_REAL_PLAYER_CARD) {
            throw new InvalidMoveException("No es momento de jugar una carta.");
        }

        Player currentPlayer = getCurrentPlayer();

        if (currentPlayer.isArtificial()) {
            throw new InvalidMoveException("El turno actual no pertenece al jugador real.");
        }

        Card selectedCard = currentPlayer.getHand().get(cardIndex);

        if (!selectedCard.canBePlayed(table.getCurrentSum())) {
            throw new InvalidMoveException("Esa carta no puede jugarse porque supera la suma de 50.");
        }

        int usedValue = selectedCard.getBestPlayableValue(table.getCurrentSum());
        Card playedCard = currentPlayer.removeCardAt(cardIndex);

        table.playCard(playedCard, usedValue);

        addLog(currentPlayer.getName() + " jugó " + playedCard + " usando valor "
                + usedValue + ". Suma actual: " + table.getCurrentSum() + ".");

        state = GameState.WAITING_REAL_PLAYER_DRAW;
    }

    public void drawForRealPlayer() throws EmptyDeckException {
        if (state != GameState.WAITING_REAL_PLAYER_DRAW) {
            throw new InvalidMoveException("Debes jugar una carta antes de tomar del mazo.");
        }

        Player currentPlayer = getCurrentPlayer();

        if (currentPlayer.isArtificial()) {
            throw new InvalidMoveException("El turno actual no pertenece al jugador real.");
        }

        currentPlayer.addCard(drawCardWithRecycle());

        addLog(currentPlayer.getName() + " tomó una carta del mazo.");
        advanceTurn();
    }

    public void playCurrentArtificialCard() {
        if (state != GameState.ARTIFICIAL_PLAYER_TURN) {
            throw new InvalidMoveException("No es turno de un jugador artificial.");
        }

        Player currentPlayer = getCurrentPlayer();

        if (!(currentPlayer instanceof ArtificialPlayer artificialPlayer)) {
            throw new InvalidMoveException("El jugador actual no es artificial.");
        }

        Optional<CardPlay> optionalPlay = artificialPlayer.choosePlay(table.getCurrentSum());

        if (optionalPlay.isEmpty()) {
            eliminatePlayer(currentPlayer);
            prepareCurrentTurn();
            return;
        }

        CardPlay play = optionalPlay.get();

        if (!currentPlayer.removeCard(play.getCard())) {
            throw new InvalidMoveException("La carta seleccionada no está en la mano del jugador.");
        }

        table.playCard(play.getCard(), play.getUsedValue());

        addLog(currentPlayer.getName() + " jugó " + play.getCard() + " usando valor "
                + play.getUsedValue() + ". Suma actual: " + table.getCurrentSum() + ".");

        state = GameState.ARTIFICIAL_PLAYER_DRAW;
    }

    public void drawForCurrentArtificialPlayer() throws EmptyDeckException {
        if (state != GameState.ARTIFICIAL_PLAYER_DRAW) {
            throw new InvalidMoveException("El jugador artificial debe jugar antes de tomar carta.");
        }

        Player currentPlayer = getCurrentPlayer();

        if (!currentPlayer.isArtificial()) {
            throw new InvalidMoveException("El turno actual no pertenece a un jugador artificial.");
        }

        currentPlayer.addCard(drawCardWithRecycle());

        addLog(currentPlayer.getName() + " tomó una carta del mazo.");
        advanceTurn();
    }

    private void advanceTurn() {
        if (state == GameState.FINISHED) {
            return;
        }

        if (checkWinner()) {
            return;
        }

        currentPlayerIndex = findNextActivePlayerIndex(currentPlayerIndex);
        prepareCurrentTurn();
    }

    private void prepareCurrentTurn() {
        if (checkWinner()) {
            return;
        }

        while (state != GameState.FINISHED) {
            Player currentPlayer = getCurrentPlayer();

            if (!currentPlayer.isActive() || !currentPlayer.hasPlayableCard(table.getCurrentSum())) {
                eliminatePlayer(currentPlayer);

                if (checkWinner()) {
                    return;
                }

                currentPlayerIndex = findNextActivePlayerIndex(currentPlayerIndex);
                continue;
            }

            if (currentPlayer.isArtificial()) {
                state = GameState.ARTIFICIAL_PLAYER_TURN;
            } else {
                state = GameState.WAITING_REAL_PLAYER_CARD;
            }

            addLog("Turno de " + currentPlayer.getName() + ".");
            return;
        }
    }

    private void eliminatePlayer(Player player) {
        if (!player.isActive()) {
            return;
        }

        player.setActive(false);
        List<Card> returnedCards = player.removeAllCards();
        deck.sendToBottom(returnedCards);

        addLog(player.getName() + " fue eliminado. Sus cartas volvieron al mazo.");
    }

    private boolean checkWinner() {
        List<Player> activePlayers = players.stream()
                .filter(Player::isActive)
                .toList();

        if (activePlayers.size() == 1) {
            Player winner = activePlayers.get(0);
            winnerName = winner.getName();
            state = GameState.FINISHED;
            addLog("Fin del juego. Ganador: " + winnerName + ".");
            return true;
        }

        return false;
    }

    private int findNextActivePlayerIndex(int fromIndex) {
        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (fromIndex + i) % players.size();

            if (players.get(nextIndex).isActive()) {
                return nextIndex;
            }
        }

        return fromIndex;
    }

    private Card drawCardWithRecycle() throws EmptyDeckException {
        if (deck.isEmpty()) {
            recycleTableCards();
        }

        return deck.draw();
    }

    private void recycleTableCards() {
        List<Card> recycledCards = table.removeCardsExceptTop();

        if (!recycledCards.isEmpty()) {
            deck.shuffleAndSendToBottom(recycledCards);
            addLog("Se reciclaron las cartas de la mesa excepto la carta superior.");
        }
    }

    private void addLog(String message) {
        gameLog.add(message);
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public Table getTable() {
        return table;
    }

    public Deck getDeck() {
        return deck;
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public List<String> getGameLog() {
        return new ArrayList<>(gameLog);
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public GameState getState() {
        return state;
    }

    public String getWinnerName() {
        return winnerName;
    }
}