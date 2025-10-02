package sk.tuke.gamestudio.core;

import java.util.Arrays;

public class Mill {
    private static final String[][] mills = {
            {"a1", "d1", "g1"}, {"b2", "d2", "f2"}, {"c3", "d3", "e3"},
            {"a4", "b4", "c4"}, {"e4", "f4", "g4"}, {"c5", "d5", "e5"},
            {"b6", "d6", "f6"}, {"a7", "d7", "g7"},
            {"a1", "a4", "a7"}, {"b2", "b4", "b6"}, {"c3", "c4", "c5"},
            {"d1", "d2", "d3"}, {"d5", "d6", "d7"}, {"e3", "e4", "e5"},
            {"f2", "f4", "f6"}, {"g1", "g4", "g7"}
    };

    public static boolean isMillFormed(String position, char symbol, Board board) {
        for (String[] mill : mills) {
            if (Arrays.asList(mill).contains(position)) {
                boolean millFormed = true;
                for (String pos : mill) {
                    if (!symbolAt(board, pos).equals(String.valueOf(symbol))) {
                        millFormed = false;
                        break;
                    }
                }
                if (millFormed) return true;
            }
        }
        return false;
    }

    private static String symbolAt(Board board, String position) {
        int[] coords = board.getPositionCoordinates(position);
        return board.getSymbolAt(coords[0], coords[1]);
    }
}

