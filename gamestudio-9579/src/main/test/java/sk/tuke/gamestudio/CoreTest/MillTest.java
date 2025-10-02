package sk.tuke.gamestudio.CoreTest;


import org.testng.annotations.Test;
import sk.tuke.gamestudio.core.Board;
import sk.tuke.gamestudio.core.Mill;


import static org.testng.AssertJUnit.assertFalse;
import static org.testng.AssertJUnit.assertTrue;

public class MillTest {

    @Test
    public void testMillIsFormed() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        board.placePiece("d1", 'X');
        board.placePiece("g1", 'X');

        assertTrue(Mill.isMillFormed("a1", 'X', board));
    }

    @Test
    public void testMillNotFormed() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        board.placePiece("d1", 'O');
        board.placePiece("g1", 'X');

        assertFalse(Mill.isMillFormed("a1", 'X', board));
    }

    @Test
    public void testMillNotFormedInitially() {
        Board board = new Board();
        assertFalse(Mill.isMillFormed("a1", 'X', board));
    }


}

