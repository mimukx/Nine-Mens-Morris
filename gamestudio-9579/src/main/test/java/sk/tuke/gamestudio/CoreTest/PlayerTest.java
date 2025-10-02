package sk.tuke.gamestudio.CoreTest;


import org.testng.annotations.Test;
import sk.tuke.gamestudio.core.Player;


import static org.testng.AssertJUnit.*;

public class PlayerTest {

    @Test
    public void testPlacingAndRemoving() {
        Player player = new Player("Test Player", 'X');

        player.placePiece();
        player.placePiece();
        player.placePiece();

        assertEquals(3, player.getPiecesOnBoard());
        assertEquals(3, player.getPiecesLeft());

        player.removePiece();
        assertEquals(2, player.getPiecesOnBoard());
    }

    @Test
    public void testIsFlyingTrue() {
        Player player = new Player("Test", 'X');
        player.placePiece();
        player.placePiece();
        player.placePiece();
        assertTrue(player.isFlying());
    }

    @Test
    public void testIsFlyingFalse() {
        Player player = new Player("Test", 'X');
        player.placePiece();
        player.placePiece();
        assertFalse(player.isFlying());
    }

    @Test
    public void testPlayerInitialPiecesLeft() {
        Player player = new Player("Test", 'X');
        assertEquals(player.getPiecesLeft(), 6);
    }

    @Test
    public void testIsFlyingWithThreePieces() {
        Player player = new Player("Test", 'X');
        for (int i = 0; i < 3; i++) {
            player.placePiece();
        }
        assertTrue(player.isFlying());
    }

}
