package sk.tuke.gamestudio.bot;


import sk.tuke.gamestudio.core.Board;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.core.Move;
import sk.tuke.gamestudio.core.Player;
import sk.tuke.gamestudio.enums.GameState;

import java.util.*;

public class BotPlayer extends Player {
    public BotPlayer(String name, char pieceSymbol) {
        super(name, pieceSymbol);
    }

    public void makeMove(Game game) {
        GameState state = game.getGameState();
        Board board = game.getBoard();

        switch (state) {
            case PLACING:
                placeRandom(board);
                break;
            case MOVING:
                moveRandom(board, false);
                break;
            case FLYING:
                moveRandom(board, true);
                break;
            case REMOVE:
                removeOpponent(board, game.getOpponent().getPieceSymbol());
                break;
        }
        game.switchPlayer();
    }

    private void placeRandom(Board board) {
        List<String> freePositions = getFreePositions(board);
        if (freePositions.isEmpty()) return;

        String position = getRandom(freePositions);
        if (board.placePiece(position, getPieceSymbol())) {
            this.placePiece();
            System.out.println(getName() + " placed at " + position);
        }
    }

    private void moveRandom(Board board, boolean flying) {
        List<String> myPieces = getMyPositions(board);
        Collections.shuffle(myPieces);

        for (String from : myPieces) {
            List<String> targets = flying ? getFreePositions(board)
                    : Arrays.asList(board.adjacencyMap.get(from));

            Collections.shuffle(targets);
            for (String to : targets) {
                if (board.isValidPosition(to) && board.isEmptyAt(to)) {
                    if (Move.attempt(board, from, to, getPieceSymbol(), flying)) {
                        System.out.println(getName() + " moved from " + from + " to " + to);
                        return;
                    }
                }
            }
        }
    }

    private void removeOpponent(Board board, char opponentSymbol) {
        for (String position : board.positionMap.keySet()) {
            String symbol = board.getSymbolAt(position);
            if (symbol.equals(String.valueOf(opponentSymbol))) {
                if (board.removePiece(position, getPieceSymbol())) {
                    System.out.println(getName() + " removed opponent's piece at " + position);
                    return;
                }
            }
        }
    }

    private List<String> getFreePositions(Board board) {
        List<String> result = new ArrayList<>();
        for (String pos : board.positionMap.keySet()) {
            if (board.isEmptyAt(pos)) result.add(pos);
        }
        return result;
    }

    private List<String> getMyPositions(Board board) {
        List<String> result = new ArrayList<>();
        for (String pos : board.positionMap.keySet()) {
            if (board.getSymbolAt(pos).equals(String.valueOf(getPieceSymbol()))) {
                result.add(pos);
            }
        }
        return result;
    }

    private String getRandom(List<String> list) {
        return list.get(new Random().nextInt(list.size()));
    }
}
