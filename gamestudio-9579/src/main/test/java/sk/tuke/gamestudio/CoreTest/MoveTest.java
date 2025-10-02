package sk.tuke.gamestudio.CoreTest;


import org.testng.annotations.Test;
import sk.tuke.gamestudio.core.Board;
import sk.tuke.gamestudio.core.Move;


import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class MoveTest {

    @Test
    public void testValidMoveAdjacent() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        board.placePiece("a4", '●');

        boolean moved = Move.attempt(board, "a1", "a4", 'X', false);
        assertTrue(moved);
    }

    @Test
    public void testInvalidMoveNotAdjacent() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        board.placePiece("g1", '●');

        boolean moved = Move.attempt(board, "a1", "g1", 'X', false);
        assertFalse(moved);
    }

    @Test
    public void testFlyingMoveAllowed() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        board.placePiece("g1", '●');

        boolean moved = Move.attempt(board, "a1", "g1", 'X', true);
        assertTrue(moved);
    }

}
