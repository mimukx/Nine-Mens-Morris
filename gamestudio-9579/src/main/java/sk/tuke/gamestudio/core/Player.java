package sk.tuke.gamestudio.core;

public class Player {
    private String name;
    private char pieceSymbol;
    private int piecesLeft;
    private int piecesOnBoard;
    private char symbol;


    public Player(String name, char pieceSymbol) {
        this.name = name;
        this.pieceSymbol = pieceSymbol;
        this.piecesLeft = 9;
        this.piecesOnBoard = 0;
    }

    public String getName() {
        return name;
    }

    public char getPieceSymbol() {
        return pieceSymbol;
    }

    public int getPiecesLeft() {
        return piecesLeft;
    }

    public void placePiece() {
        if (piecesLeft > 0) {
            piecesLeft--;
            piecesOnBoard++;
        }
    }
    public char getSymbol() {
        return symbol;
    }

    public int getPiecesOnBoard() {
        return piecesOnBoard;
    }


    public void removePiece() {
        if (piecesOnBoard > 0) {
            piecesOnBoard--;
        }
    }

    public boolean isFlying() {
        return piecesOnBoard == 3;
    }
}