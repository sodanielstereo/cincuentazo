package com.cincuentazo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

import com.cincuentazo.exceptions.EmptyDeckException;
import com.cincuentazo.exceptions.GameConfigurationException;

/**
 * Unit tests for the {@link Game} class.
 */
class GameTest {

    /**
     * Verifies that a game starts with the custom human player name.
     */
    @Test
    void gameStartsWithCustomRealPlayerName() throws GameConfigurationException, EmptyDeckException {
        Game game = new Game("Daniel", 3);

        assertEquals("Daniel", game.getPlayers().get(0).getName());
        assertEquals(4, game.getPlayers().size());
    }

    /**
     * Verifies that the game creates the correct number of players.
     */
    @Test
    void gameCreatesCorrectAmountOfPlayers() throws GameConfigurationException, EmptyDeckException {
        Game gameWithOneMachine = new Game("Daniel", 1);
        Game gameWithThreeMachines = new Game("Daniel", 3);

        assertEquals(2, gameWithOneMachine.getPlayers().size());
        assertEquals(4, gameWithThreeMachines.getPlayers().size());
    }

    /**
     * Verifies that all players start with four cards.
     */
    @Test
    void allPlayersStartWithFourCards() throws GameConfigurationException, EmptyDeckException {
        Game game = new Game("Daniel", 3);

        for (Player player : game.getPlayers()) {
            assertEquals(4, player.getHand().size());
        }
    }

    /**
     * Verifies that the game starts with an initial table card and log entry.
     */
    @Test
    void gameStartsWithInitialTableCardAndLog() throws GameConfigurationException, EmptyDeckException {
        Game game = new Game("Daniel", 2);

        assertNotNull(game.getTable().getTopCard());
        assertFalse(game.getGameLog().isEmpty());
        assertTrue(game.getGameLog().get(0).contains("Carta inicial"));
    }

    /**
     * Verifies that the game rejects an empty human player name.
     */
    @Test
    void gameRejectsEmptyRealPlayerName() {
        assertThrows(GameConfigurationException.class, () -> new Game("", 1));
        assertThrows(GameConfigurationException.class, () -> new Game("   ", 1));
        assertThrows(GameConfigurationException.class, () -> new Game(null, 1));
    }

    /**
     * Verifies that the game rejects an invalid number of artificial players.
     */
    @Test
    void gameRejectsInvalidArtificialPlayerAmount() {
        assertThrows(GameConfigurationException.class, () -> new Game("Daniel", 0));
        assertThrows(GameConfigurationException.class, () -> new Game("Daniel", 4));
    }

    /**
     * Verifies that the human player can play a card and must then draw.
     */
    @Test
    void realPlayerCanPlayAndThenMustDraw() throws GameConfigurationException, EmptyDeckException {
        Game game = new Game("Daniel", 1);

        Player realPlayer = game.getPlayers().get(0);
        int initialHandSize = realPlayer.getHand().size();

        int playableCardIndex = findPlayableCardIndex(game, realPlayer);

        game.playRealPlayerCard(playableCardIndex);

        assertEquals(GameState.WAITING_REAL_PLAYER_DRAW, game.getState());
        assertEquals(initialHandSize - 1, realPlayer.getHand().size());

        game.drawForRealPlayer();

        assertEquals(initialHandSize, realPlayer.getHand().size());
    }

    /**
     * Finds the index of the first playable card in a player's hand.
     *
     * @param game game instance
     * @param player player to search
     * @return index of a playable card
     */
    private int findPlayableCardIndex(Game game, Player player) {
        for (int i = 0; i < player.getHand().size(); i++) {
            Card card = player.getHand().get(i);

            if (card.canBePlayed(game.getTable().getCurrentSum())) {
                return i;
            }
        }

        fail("El jugador real debería tener al menos una carta jugable al inicio.");
        return -1;
    }
}
