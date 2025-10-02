package sk.tuke.gamestudio.CoreTest;


import org.junit.Test;
import sk.tuke.gamestudio.core.Board;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testPlacePieceOnValidPosition() {
        Board board = new Board();
        assertTrue(board.placePiece("a1", 'X'));
    }

    @Test
    public void testPlacePieceOnOccupiedPosition() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        assertFalse(board.placePiece("a1", 'O'));
    }

    @Test
    public void testRemoveOpponentPiece() {
        Board board = new Board();
        board.placePiece("a1", 'O');
        assertTrue(board.removePiece("a1", 'X'));
    }

    @Test
    public void testCannotRemoveOwnPiece() {
        Board board = new Board();
        board.placePiece("a1", 'X');
        assertFalse(board.removePiece("a1", 'X'));
    }

    @Test
    public void testInvalidPosition() {
        Board board = new Board();
        assertFalse(board.placePiece("z9", 'X'));
    }

    @Test
    public void testPlacePieceOnMiddlePosition() {
        Board board = new Board();
        assertTrue(board.placePiece("d1", 'X'));
    }

    @Test
    public void testRemovePieceFromEmptyPosition() {
        Board board = new Board();
        assertFalse(board.removePiece("a1", 'X'));
    }

}


