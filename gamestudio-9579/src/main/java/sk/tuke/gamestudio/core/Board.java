package sk.tuke.gamestudio.core;


import java.util.HashMap;
import java.util.Map;

public class Board {
    private final String[][] board;
    public final Map<String, int[]> positionMap;

    public Board() {

        board = new String[][]{
                {"1", "●", "───", "───", "●", "───", "───", "●"},
                {" ", "|","   ", "   ", "|", "   ", "   ", "|"},
                {"2", "|","   ","●", "─", "●", "─", "●", "    |"},
                {"3", "|","   ","|", "·", "·", "·", "|","   ", "|"},
                {"4", "●","───","●", "·", " ", "·", "●","───", "●"},
                {"5", "|","   ","|", "·", "·", "·", "|", "   ","|"},
                {"6", "|","   ","●", "─", "●", "─", "●", "    |"},
                {" ", "|", "   ", "   ", "|", "   ", "   ", "|"},
                {"7", "●", "───", "───", "●", "───", "───", "●"}
        };


        positionMap = new HashMap<>();
        positionMap.put("a1", new int[]{0, 1});
        positionMap.put("d1", new int[]{0, 4});
        positionMap.put("g1", new int[]{0, 7});

        positionMap.put("b2", new int[]{2, 3});
        positionMap.put("d2", new int[]{2, 5});
        positionMap.put("f2", new int[]{2, 7});

        positionMap.put("c3", new int[]{3, 4});
        positionMap.put("d3", new int[]{3, 5});
        positionMap.put("e3", new int[]{3, 6});

        positionMap.put("a4", new int[]{4, 1});
        positionMap.put("b4", new int[]{4, 3});
        positionMap.put("c4", new int[]{4, 4});
        positionMap.put("e4", new int[]{4, 6});
        positionMap.put("f4", new int[]{4, 7});
        positionMap.put("g4", new int[]{4, 9});

        positionMap.put("c5", new int[]{5, 4});
        positionMap.put("d5", new int[]{5, 5});
        positionMap.put("e5", new int[]{5, 6});

        positionMap.put("b6", new int[]{6, 3});
        positionMap.put("d6", new int[]{6, 5});
        positionMap.put("f6", new int[]{6, 7});

        positionMap.put("a7", new int[]{8, 1});
        positionMap.put("d7", new int[]{8, 4});
        positionMap.put("g7", new int[]{8, 7});



        initializeAdjacencyMap();


    }

    public boolean hasAvailableMoves(char symbol, boolean isFlying) {
        if (isFlying) return true;

        for (Map.Entry<String, int[]> entry : positionMap.entrySet()) {
            String from = entry.getKey();
            int[] coords = entry.getValue();
            String cell = board[coords[0]][coords[1]];

            if (cell.equals(String.valueOf(symbol))) {
                String[] neighbors = adjacencyMap.get(from);
                if (neighbors == null) continue;

                for (String neighbor : neighbors) {
                    int[] nCoords = positionMap.get(neighbor);
                    String neighborCell = board[nCoords[0]][nCoords[1]];
                    if (neighborCell.equals("●") || neighborCell.equals("·")) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public int[] getPositionCoordinates(String position) {
        return positionMap.get(position);
    }

    public String getSymbolAt(String position) {
        int[] coords = getPositionCoordinates(position);
        return board[coords[0]][coords[1]];
    }

    public String[][] getBoardArray() {
        return board;
    }

    public String getSymbolAt(int row, int col) {
        return board[row][col];
    }

    public void setSymbolAt(String position, String symbol) {
        int[] coords = getPositionCoordinates(position);
        board[coords[0]][coords[1]] = symbol;
    }

    public boolean isValidPosition(String position) {
        return positionMap.containsKey(position);
    }

    public boolean isEmptyAt(String position) {
        String cell = getSymbolAt(position);
        return cell.equals("●") || cell.equals("·");
    }

    public boolean removePiece(String position, char currentPlayerSymbol) {
        if (!positionMap.containsKey(position)) {
            System.out.println("Invalid position.");
            return false;
        }

        int[] coords = positionMap.get(position);
        int row = coords[0];
        int col = coords[1];

        String cell = board[row][col];

        if (!cell.equals("X") && !cell.equals("O")) {
            System.out.println("No opponent piece at this position.");
            return false;
        }

        if (cell.equals(String.valueOf(currentPlayerSymbol))) {
            System.out.println("You can't remove your own piece!");
            return false;
        }

        board[row][col] = "●";
        return true;
    }



    public boolean placePiece(String position, char symbol) {
        if (!positionMap.containsKey(position)) {
            System.out.println("Invalid position: " + position);
            return false;
        }

        int[] coords = positionMap.get(position);
        int row = coords[0];
        int col = coords[1];

        if (!(board[row][col].equals("●") || board[row][col].equals("·"))) {
            System.out.println("Invalid move. Spot occupied or not allowed. Try again.");
            return false;
        }

        board[row][col] = String.valueOf(symbol);
        return true;
    }


    String getOriginalSymbol(String position) {

        return (position.equals("c3") || position.equals("d3") || position.equals("e3") ||
                position.equals("c5") || position.equals("d5") || position.equals("e5") ||
                position.equals("c4") || position.equals("e4")) ? "·" : "●";
    }




    boolean isAdjacent(String from, String to) {
        if (!adjacencyMap.containsKey(from)) return false;
        for (String neighbor : adjacencyMap.get(from)) {
            if (neighbor.equals(to)) return true;
        }
        return false;
    }




    public void printBoard() {
        System.out.println("  a     b c d e f     g");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public final Map<String, String[]> adjacencyMap = new HashMap<>();

    private void initializeAdjacencyMap() {
        adjacencyMap.put("a1", new String[]{"d1", "a4"});
        adjacencyMap.put("d1", new String[]{"a1", "g1", "d2"});
        adjacencyMap.put("g1", new String[]{"d1", "g4"});

        adjacencyMap.put("b2", new String[]{"d2", "b4"});
        adjacencyMap.put("d2", new String[]{"b2", "f2", "d1", "d3"});
        adjacencyMap.put("f2", new String[]{"d2", "f4"});

        adjacencyMap.put("c3", new String[]{"d3", "c4"});
        adjacencyMap.put("d3", new String[]{"c3", "e3", "d2"});
        adjacencyMap.put("e3", new String[]{"d3", "e4"});

        adjacencyMap.put("a4", new String[]{"a1", "a7", "b4"});
        adjacencyMap.put("b4", new String[]{"a4", "c4", "b2", "b6"});
        adjacencyMap.put("c4", new String[]{"b4", "c3", "c5"});

        adjacencyMap.put("e4", new String[]{"e3", "e5", "f4"});
        adjacencyMap.put("f4", new String[]{"e4", "g4", "f2", "f6"});
        adjacencyMap.put("g4", new String[]{"f4", "g1", "g7"});

        adjacencyMap.put("c5", new String[]{"c4", "d5"});
        adjacencyMap.put("d5", new String[]{"c5", "e5", "d6"});
        adjacencyMap.put("e5", new String[]{"d5", "e4"});

        adjacencyMap.put("b6", new String[]{"b4", "d6"});
        adjacencyMap.put("d6", new String[]{"b6", "f6", "d5", "d7"});
        adjacencyMap.put("f6", new String[]{"d6", "f4"});

        adjacencyMap.put("a7", new String[]{"a4", "d7"});
        adjacencyMap.put("d7", new String[]{"a7", "g7", "d6"});
        adjacencyMap.put("g7", new String[]{"d7", "g4"});
    }

}
