package com.cincuentazo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.cincuentazo.exceptions.EmptyDeckException;
import com.cincuentazo.exceptions.GameConfigurationException;
import com.cincuentazo.exceptions.InvalidMoveException;

/**
 * Main game model class for Cincuentazo.
 * Manages players, deck, table, turns, elimination, and winner detection.
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
     * Creates a new Cincuentazo match with a custom player name.
     *
     * @param realPlayerName name of the human player
     * @param artificialPlayers number of artificial players
     * @throws GameConfigurationException if the game configuration is invalid
     * @throws EmptyDeckException if there are not enough cards to prepare the game
     */
    public Game(String realPlayerName, int artificialPlayers) throws GameConfigurationException, EmptyDeckException {
        validateRealPlayerName(realPlayerName);
        validateArtificialPlayers(artificialPlayers);

        this.deck = new Deck();
        this.table = new Table();
        this.players = new ArrayList<>();
        this.gameLog = new ArrayList<>();
        this.currentPlayerIndex = 0;
        this.state = GameState.NOT_STARTED;
        this.winnerName = "";

        createPlayers(realPlayerName, artificialPlayers);
        prepareGame();
    }

    /**
     * Secondary constructor that creates a match with a default player name.
     * Kept to simplify unit testing.
     *
     * @param artificialPlayers number of artificial players
     * @throws GameConfigurationException if the game configuration is invalid
     * @throws EmptyDeckException if there are not enough cards to prepare the game
     */
    public Game(int artificialPlayers) throws GameConfigurationException, EmptyDeckException {
        this("Jugador", artificialPlayers);
    }

    /**
     * Validates the human player's name.
     *
     * @param realPlayerName entered player name
     * @throws GameConfigurationException if the name is empty
     */
    private void validateRealPlayerName(String realPlayerName) throws GameConfigurationException {
        if (realPlayerName == null || realPlayerName.trim().isEmpty()) {
            throw new GameConfigurationException("El nombre del jugador no puede estar vacío.");
        }
    }

    /**
     * Validates the number of artificial players.
     *
     * @param artificialPlayers selected number of artificial players
     * @throws GameConfigurationException if the number is not between 1 and 3
     */
    private void validateArtificialPlayers(int artificialPlayers) throws GameConfigurationException {
        if (artificialPlayers < MIN_ARTIFICIAL_PLAYERS || artificialPlayers > MAX_ARTIFICIAL_PLAYERS) {
            throw new GameConfigurationException("Debes seleccionar entre 1 y 3 jugadores artificiales.");
        }
    }

    /**
     * Creates the human player and artificial players.
     *
     * @param realPlayerName name of the human player
     * @param artificialPlayers number of artificial players
     */
    private void createPlayers(String realPlayerName, int artificialPlayers) {
        players.add(new RealPlayer(realPlayerName.trim()));

        for (int i = 1; i <= artificialPlayers; i++) {
            players.add(new ArtificialPlayer("Máquina " + i, new SafeStrategy()));
        }
    }

    /**
     * Prepares the match by dealing cards and placing the initial table card.
     *
     * @throws EmptyDeckException if there are not enough cards
     */
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

    /**
     * Plays a card from the human player's hand.
     *
     * @param cardIndex index of the card in the hand
     * @throws InvalidMoveException if the move is not allowed
     */
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

    /**
     * Allows the human player to draw a card from the deck after playing.
     *
     * @throws EmptyDeckException if no cards are available
     * @throws InvalidMoveException if the move is not allowed
     */
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

    /**
     * Executes the current artificial player's card play.
     *
     * @throws InvalidMoveException if the move is not allowed
     */
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

    /**
     * Allows the current artificial player to draw a card and end their turn.
     *
     * @throws EmptyDeckException if no cards are available
     * @throws InvalidMoveException if the move is not allowed
     */
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

    /**
     * Advances to the next active player.
     */
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

    /**
     * Prepares the current player's turn.
     * Eliminates the player if they have no playable cards.
     */
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

    /**
     * Eliminates a player and sends their cards to the bottom of the deck.
     *
     * @param player eliminated player
     */
    private void eliminatePlayer(Player player) {
        if (!player.isActive()) {
            return;
        }

        player.setActive(false);
        List<Card> returnedCards = player.removeAllCards();
        deck.sendToBottom(returnedCards);

        addLog(player.getName() + " fue eliminado. Sus cartas volvieron al mazo.");
    }

    /**
     * Checks whether only one active player remains.
     *
     * @return {@code true} if the game has finished
     */
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

    /**
     * Finds the next active player index.
     *
     * @param fromIndex index from which to start searching
     * @return index of the next active player
     */
    private int findNextActivePlayerIndex(int fromIndex) {
        for (int i = 1; i <= players.size(); i++) {
            int nextIndex = (fromIndex + i) % players.size();

            if (players.get(nextIndex).isActive()) {
                return nextIndex;
            }
        }

        return fromIndex;
    }

    /**
     * Draws a card from the deck. Recycles table cards if the deck is empty.
     *
     * @return drawn card
     * @throws EmptyDeckException if no cards are available
     */
    private Card drawCardWithRecycle() throws EmptyDeckException {
        if (deck.isEmpty()) {
            recycleTableCards();
        }

        return deck.draw();
    }

    /**
     * Recycles table cards except for the top card.
     */
    private void recycleTableCards() {
        List<Card> recycledCards = table.removeCardsExceptTop();

        if (!recycledCards.isEmpty()) {
            deck.shuffleAndSendToBottom(recycledCards);
            addLog("Se reciclaron las cartas de la mesa excepto la carta superior.");
        }
    }

    /**
     * Adds a message to the game log.
     *
     * @param message message to record
     */
    private void addLog(String message) {
        gameLog.add(message);
    }

    /**
     * Returns the player whose turn it is.
     *
     * @return current player
     */
    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    /**
     * Returns the game table.
     *
     * @return table instance
     */
    public Table getTable() {
        return table;
    }

    /**
     * Returns the game deck.
     *
     * @return deck instance
     */
    public Deck getDeck() {
        return deck;
    }

    /**
     * Returns an unmodifiable list of all players.
     *
     * @return player list
     */
    public List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    /**
     * Returns a copy of the game event log.
     *
     * @return game log messages
     */
    public List<String> getGameLog() {
        return new ArrayList<>(gameLog);
    }

    /**
     * Returns the index of the current player.
     *
     * @return current player index
     */
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * Returns the current game state.
     *
     * @return game state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Returns the winner's name when the game is finished.
     *
     * @return winner name, or an empty string if the game is not finished
     */
    public String getWinnerName() {
        return winnerName;
    }
}
