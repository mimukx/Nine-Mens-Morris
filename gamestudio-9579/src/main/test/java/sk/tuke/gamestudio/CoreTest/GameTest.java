package sk.tuke.gamestudio.CoreTest;



import org.testng.annotations.Test;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.core.Player;
import sk.tuke.gamestudio.enums.GameState;

import static org.testng.Assert.assertNotEquals;
import static org.testng.AssertJUnit.assertEquals;


public class GameTest {

    @Test
    public void testGameStartsInPlacingState() {
        Game game = new Game();
        assertEquals(GameState.PLACING, game.getGameState());
    }

    @Test
    public void testSwitchPlayerChangesCurrent() {
        Game game = new Game();
        Player original = game.getCurrentPlayer();
        game.switchPlayer();
        assertNotEquals(original, game.getCurrentPlayer());
    }

    @Test
    public void testGetOpponentReturnsCorrectPlayer() {
        Game game = new Game();
        Player current = game.getCurrentPlayer();
        Player opponent = game.getOpponent();
        assertNotEquals(current, opponent);
    }

    @Test
    public void testInitialPlayerIsPlayer1() {
        Game game = new Game();
        assertEquals(game.getCurrentPlayer(), game.getPlayer1());
    }

    @Test
    public void testSwitchPlayerChangesTurn() {
        Game game = new Game();
        Player first = game.getCurrentPlayer();
        game.switchPlayer();
        assertNotEquals(game.getCurrentPlayer(), first);
    }

}
