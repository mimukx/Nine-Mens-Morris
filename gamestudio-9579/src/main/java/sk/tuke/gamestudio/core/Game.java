package sk.tuke.gamestudio.core;


import sk.tuke.gamestudio.bot.BotPlayer;
import sk.tuke.gamestudio.enums.GameState;

public class Game {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GameState gameState;


    public Game() {
        this.board = new Board();
        this.player1 = new Player("Player 1", 'X');
        this.player2 = new Player("Player 2", 'O');
        this.currentPlayer = player1;
        this.gameState = GameState.PLACING;
    }

    public String getBoardAsString() {
        return board.toString();
    }



    public Game(boolean vsBot) {
        this.board = new Board();
        this.player1 = new Player("Player", 'X');
        if (vsBot) {
            this.player2 = new BotPlayer("Bot", 'O');
        } else {
            this.player2 = new Player("Player 2", 'O');
        }
        this.currentPlayer = player1;
        this.gameState = GameState.PLACING;
    }



    public void processCommand(String command) {
        if (command.equalsIgnoreCase("exit")) {
            this.gameState = GameState.GAME_OVER;
            return;
        }

        try {
            switch (gameState) {
                case PLACING -> {
                    if (board.placePiece(command, currentPlayer.getPieceSymbol())) {
                        currentPlayer.placePiece();
                        if (Mill.isMillFormed(command, currentPlayer.getPieceSymbol(), board)) {
                            gameState = GameState.REMOVE;
                        } else {
                            switchPlayer();
                        }
                        if (currentPlayer.getPiecesLeft() == 0 && getOpponent().getPiecesLeft() == 0)
                            gameState = GameState.MOVING;
                    }
                }

                case MOVING, FLYING -> {
                    String[] parts = parsePositions(command);
                    if (parts == null) return;

                    boolean moved = Move.attempt(board, parts[0], parts[1], currentPlayer.getPieceSymbol(), currentPlayer.isFlying());
                    if (moved) {
                        if (Mill.isMillFormed(parts[1], currentPlayer.getPieceSymbol(), board)) {
                            gameState = GameState.REMOVE;
                        } else {
                            switchPlayer();
                        }
                    }
                }

                case REMOVE -> {
                    if (board.removePiece(command, currentPlayer.getPieceSymbol())) {
                        getOpponent().removePiece();
                        gameState = (currentPlayer.isFlying() || getOpponent().isFlying()) ? GameState.FLYING : GameState.MOVING;
                        if (getOpponent().getPiecesOnBoard() < 3 || !board.hasAvailableMoves(getOpponent().getPieceSymbol(), getOpponent().isFlying())) {
                            gameState = GameState.GAME_OVER;
                        } else {
                            switchPlayer();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
    }

    private String[] parsePositions(String input) {
        if (input.contains("-")) {
            return input.split("-");
        }
        return null;
    }


    public char getCurrentPlayerSymbol() {
        return currentPlayer.getSymbol();
    }



    public Player getOpponent() {
        return (currentPlayer == player1) ? player2 : player1;
    }


    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }

    public Board getBoard() {
        return board;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}