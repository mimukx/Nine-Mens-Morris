//package sk.tuke.gamestudio.ui;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import sk.tuke.gamestudio.bot.BotPlayer;
//import sk.tuke.gamestudio.core.Game;
//import sk.tuke.gamestudio.core.Mill;
//import sk.tuke.gamestudio.core.Move;
//import sk.tuke.gamestudio.core.Player;
//import sk.tuke.gamestudio.entity.Comment;
//import sk.tuke.gamestudio.entity.Rating;
//import sk.tuke.gamestudio.entity.Score;
//import sk.tuke.gamestudio.enums.GameState;
//import sk.tuke.gamestudio.service.*;
//
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//
//@Component
//public class ConsoleUI {
//    private Game game;
//    private final Scanner scanner = new Scanner(System.in);
//    private String currentUsername;
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private ScoreService scoreService;
//
//    @Autowired
//    private CommentService commentService;
//
//    @Autowired
//    private RatingService ratingService;
//
//    public void start() {
//        login();
//        showMenu();
//    }
//
//
//
////    private void login() {
////        System.out.println("Enter your username:");
////        String username = scanner.nextLine().trim();
////        currentUsername = username;
////        try {
////            userService.register(username);
////        } catch (UserException e) {
////            System.err.println("Login error: " + e.getMessage());
////        }
////    }
//
//
//
//
//    private void showMenu() {
//        while (true) {
//            System.out.println("\nHello " + currentUsername + "! Choose:");
//            System.out.println("1) Play Game");
//            System.out.println("2) Rate Game");
//            System.out.println("3) Leave Comment");
//            System.out.println("4) See Score");
//            System.out.println("5) View Comments and Ratings");
//            System.out.println("Type a number or 'exit' to quit:");
//
//            String input = scanner.nextLine().trim();
//
//            switch (input) {
//                case "1" -> playGame();
//                case "2" -> rateGame();
//                case "3" -> leaveComment();
//                case "4" -> seeScore();
//                case "5" -> viewCommentsAndRatings();
//                case "exit" -> {
//                    return;
//                }
//                default -> System.out.println("Invalid input.");
//            }
//        }
//    }
//
//    private void playGame() {
//        System.out.println("Choose game mode:\n1) Player vs Player\n2) Player vs Bot");
//        String choice = scanner.nextLine().trim();
//
//        game = new Game(choice.equals("2"));
//        play();
//    }
//
//    public void play() {
//        System.out.println("Welcome to Nine Men's Morris!");
//        game.getBoard().printBoard();
//
//        while (game.getGameState() != GameState.GAME_OVER) {
//            printGameInfo();
//
//            if (game.getCurrentPlayer() instanceof BotPlayer bot) {
//                bot.makeMove(game);
//                handleStateAfterBotMove();
//                game.getBoard().printBoard();
//                continue;
//            }
//
//            switch (game.getGameState()) {
//                case PLACING -> handlePlacing();
//                case MOVING, FLYING -> handleMovingOrFlying();
//            }
//
//            game.getBoard().printBoard();
//        }
//
//        System.out.println("Thanks for playing!");
//    }
//
//    private void handleStateAfterBotMove() {
//        if (game.getGameState() == GameState.PLACING) {
//            if (game.getPlayer1().getPiecesLeft() == 0 && game.getPlayer2().getPiecesLeft() == 0) {
//                game.setGameState(GameState.MOVING);
//            } else {
//                game.switchPlayer();
//            }
//        } else if (game.getGameState() == GameState.MOVING || game.getGameState() == GameState.FLYING) {
//            Player opponent = game.getOpponent();
//            if (opponent.getPiecesOnBoard() < 3 || !game.getBoard().hasAvailableMoves(opponent.getPieceSymbol(), opponent.isFlying())) {
//                System.out.println("\n🏁 Game Over! " + game.getCurrentPlayer().getName() + " wins!");
//                game.setGameState(GameState.GAME_OVER);
//            } else {
//                game.switchPlayer();
//            }
//        }
//    }
//
//    private void handlePlacing() {
//        System.out.println("Enter position to place a piece (e.g., a1), or type 'exit':");
//        String input = scanner.nextLine();
//        if (checkExit(input)) return;
//
//        placePiece(input);
//
//        if (game.getPlayer1().getPiecesLeft() == 0 && game.getPlayer2().getPiecesLeft() == 0) {
//            System.out.println("\n✨ All pieces placed! MOVING phase starts.");
//            game.setGameState(GameState.MOVING);
//        } else {
//            game.switchPlayer();
//        }
//    }
//
//    private void handleMovingOrFlying() {
//        System.out.println("Enter move (from to), e.g., a1 a2, or 'exit':");
//        String input = scanner.nextLine();
//        if (checkExit(input)) return;
//
//        String[] parts = input.split(" ");
//        if (parts.length != 2) {
//            System.out.println("Invalid format!");
//            return;
//        }
//
//        movePiece(parts[0], parts[1]);
//
//        Player opponent = game.getOpponent();
//        boolean hasLessThanThree = opponent.getPiecesOnBoard() < 3;
//        boolean canMove = game.getBoard().hasAvailableMoves(opponent.getPieceSymbol(), opponent.isFlying());
//
//        if (hasLessThanThree || !canMove) {
//            System.out.println("\n🏁 Game Over! " + game.getCurrentPlayer().getName() + " wins!");
//            game.setGameState(GameState.GAME_OVER);
//            recordScore(1);
//        } else {
//            char winnerSymbol = game.getCurrentPlayer().getSymbol();
//            game.switchPlayer();
//            recordScore(game.getCurrentPlayerSymbol() == winnerSymbol ? 1 : 0);
//        }
//    }
//
//    private void placePiece(String position) {
//        if (!game.getBoard().placePiece(position, game.getCurrentPlayer().getPieceSymbol())) {
//            System.out.println("Invalid move.");
//            return;
//        }
//
//        game.getCurrentPlayer().placePiece();
//
//        if (Mill.isMillFormed(position, game.getCurrentPlayer().getPieceSymbol(), game.getBoard())) {
//            System.out.println("🎉 Mill formed! Remove opponent piece:");
//            removeOpponentPiece();
//        }
//    }
//
//    private void movePiece(String from, String to) {
//        boolean isFlying = game.getCurrentPlayer().isFlying();
//        boolean moved = Move.attempt(game.getBoard(), from, to, game.getCurrentPlayer().getPieceSymbol(), isFlying);
//
//        if (!moved) {
//            System.out.println("Invalid move.");
//            return;
//        }
//
//        if (Mill.isMillFormed(to, game.getCurrentPlayer().getPieceSymbol(), game.getBoard())) {
//            System.out.println("🎉 Mill formed! Remove opponent piece:");
//            removeOpponentPiece();
//        }
//    }
//
//    private void removeOpponentPiece() {
//        System.out.println("Enter position of opponent’s piece to remove:");
//        while (true) {
//            String input = scanner.nextLine();
//            if (game.getBoard().removePiece(input, game.getCurrentPlayer().getPieceSymbol())) {
//                game.getOpponent().removePiece();
//                break;
//            }
//            System.out.println("Invalid. Try again:");
//        }
//    }
//
//    private boolean checkExit(String input) {
//        if (input.equalsIgnoreCase("exit")) {
//            System.out.println("👋 Exiting game.");
//            game.setGameState(GameState.GAME_OVER);
//            return true;
//        }
//        return false;
//    }
//
//    private void printGameInfo() {
//        Player currentPlayer = game.getCurrentPlayer();
//        System.out.printf("\n--- %s (%c) | Pieces Left: %d ---\n", currentPlayer.getName(), currentPlayer.getPieceSymbol(), currentPlayer.getPiecesLeft());
//    }
//
//    private void seeScore() {
//        List<Score> scores = scoreService.getTopScores("Nine Men's Morris");
//
//        long wins = scores.stream()
//                .filter(s -> s.getPlayer().equals(currentUsername) && s.getPoints() == 1)
//                .count();
//
//        long losses = scores.stream()
//                .filter(s -> s.getPlayer().equals(currentUsername) && s.getPoints() == 0)
//                .count();
//
//        System.out.printf("Your Wins: %d | Losses: %d%n", wins, losses);
//    }
//
//    private void viewCommentsAndRatings() {
//        List<Comment> comments = commentService.getComments("Nine Men's Morris");
//        System.out.println("=== Comments and Ratings ===");
//        for (Comment c : comments) {
//            System.out.printf("%s (%s): %s%n", c.getPlayer(), c.getCommentedOn(), c.getComment());
//        }
//
//        int avgRating = ratingService.getAverageRating("Nine Men's Morris");
//        System.out.println("Average rating: " + avgRating + " / 5");
//    }
//
//    private void leaveComment() {
//        System.out.println("Write your comment:");
//        String text = scanner.nextLine().trim();
//
//        Comment comment = new Comment("Nine Men's Morris", currentUsername, text, new Date());
//        commentService.addComment(comment);
//        System.out.println("Thanks! Comment saved.");
//    }
//
//    private void rateGame() {
//        System.out.print("Rate the game (1-5): ");
//        int value = Integer.parseInt(scanner.nextLine());
//
//        ratingService.setRating(new Rating("Nine Men's Morris", currentUsername, value, new Date()));
//        System.out.println("Thanks! Rating saved.");
//    }
//
//    private void recordScore(int points) {
//        scoreService.addScore(new Score("Nine Men's Morris", currentUsername, points, new Date()));
//    }
//}
