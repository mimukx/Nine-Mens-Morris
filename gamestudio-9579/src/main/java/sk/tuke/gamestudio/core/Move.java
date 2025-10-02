package sk.tuke.gamestudio.core;

public class Move {

    public static boolean attempt(Board board, String from, String to, char symbol, boolean isFlying) {
        if (!board.isValidPosition(from) || !board.isValidPosition(to)) {
            System.out.println("Invalid position.");
            return false;
        }

        if (!board.getSymbolAt(from).equals(String.valueOf(symbol))) {
            System.out.println("You can only move your own pieces!");
            return false;
        }

        if (!board.isEmptyAt(to)) {
            System.out.println("Destination is occupied.");
            return false;
        }

        if (!isFlying && !board.isAdjacent(from, to)) {
            System.out.println("You can only move to an adjacent position.");
            return false;
        }

        board.setSymbolAt(from, board.getOriginalSymbol(from));
        board.setSymbolAt(to, String.valueOf(symbol));
        return true;
    }
}
