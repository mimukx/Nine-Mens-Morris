package sk.tuke.gamestudio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.bot.BotPlayer;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.enums.GameState;
import sk.tuke.gamestudio.repository.RatingRepository;
import sk.tuke.gamestudio.service.CommentService;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
public class NavigationController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    private final Game game = new Game();



    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

//    @GetMapping("/register")
//    public String registerStep1() {
//        return "register_step1";
//    }


    @GetMapping("/home")
    public String home(HttpSession session, Model model) {
        String username = (String) session.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        model.addAttribute("username", username);
        model.addAttribute("comments", commentService.getComments("morris"));
        model.addAttribute("score", scoreService.getScoreForUser("morris", username));
        model.addAttribute("rating", ratingService.getAverageRating("morris"));

        return "home";
    }

    @GetMapping("/comments")
    public String commentsPage(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        model.addAttribute("username", username);
        model.addAttribute("comments", commentService.getComments("morris"));
        return "comments";
    }


    @PostMapping("/comment")
    public String postComment(@RequestParam String comment, HttpSession session) {
        String username = (String) session.getAttribute("username");
        if (username != null && !comment.isBlank()) {
            commentService.addComment(new Comment("morris", username, comment, new Date()));
        }
        return "redirect:/home";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username,
                               @RequestParam String password,
                               Model model,
                               HttpSession session) {
        if (userService.login(username, password)) {
            session.setAttribute("username", username);
            return "redirect:/home";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
    }


    @RequestMapping(method = RequestMethod.POST)
    public String processRegister(@RequestParam String username,
                                  @RequestParam String password,
                                  @RequestParam String email,
                                  Model model) {
        if (!userService.exists(username)) {
            userService.register(username, email, password);
            return "redirect:/login";
        } else {
            model.addAttribute("error", "User already exists");
            return "register_step1";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/rules")
    public String rules() {
        return "rules";
    }


    @PostMapping("/rating")
    public String submitRating(@RequestParam("rating") int rating, HttpSession session) {
        String username = (String) session.getAttribute("username");

        if (username != null) {
            ratingRepository.findRatingByGameAndUsername("morris", username)
                    .ifPresent(ratingRepository::delete);

            Rating newRating = new Rating("morris", username, rating, new Date());
            ratingRepository.save(newRating);
        }

        return "redirect:/rating";
    }

    @GetMapping("/rating")
    public String rating(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");

        model.addAttribute("ratings", ratingRepository.findAllByGameOrderByRatedOnDesc("morris"));

        if (username != null) {
            model.addAttribute("userRating",
                    ratingRepository.findRatingByGameAndUsername("morris", username)
                            .map(Rating::getRating)
                            .orElse(null));
        }

        return "rating";
    }

    @GetMapping("/board")
    public String gameBoard(Model model, HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game == null) {
            game = new Game();
            session.setAttribute("game", game);
        }

        model.addAttribute("currentPlayer", game.getCurrentPlayer().getPieceSymbol());
        model.addAttribute("phase", game.getGameState());
        model.addAttribute("board", game.getBoard().getBoardArray());

        return "board";
    }

    @PostMapping("/startGame")
    public String startGame(@RequestParam String mode, HttpSession session) {
        Game game = mode.equals("bot") ? new Game(true) : new Game(false);
        session.setAttribute("game", game);
        return "redirect:/board";
    }

    @GetMapping("/game")
    public String gameModePage() {
        return "game";
    }

    @PostMapping("/move")
    @ResponseBody
    public Map<String, Object> handleMove(@RequestParam String position, HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        Map<String, Object> response = new HashMap<>();

        if (game != null && game.getGameState() != GameState.GAME_OVER) {
            game.processCommand(position);

            if (game.getCurrentPlayer() instanceof BotPlayer &&
                    game.getGameState() != GameState.REMOVE) {
                ((BotPlayer) game.getCurrentPlayer()).makeMove(game);
            }

            Map<String, String> positions = new HashMap<>();
            for (String pos : game.getBoard().positionMap.keySet()) {
                positions.put(pos, game.getBoard().getSymbolAt(pos));
            }

            response.put("positions", positions);
            response.put("currentPlayer", game.getCurrentPlayer().getPieceSymbol());
            response.put("phase", game.getGameState().toString());
        }

        return response;
    }

    @GetMapping("/board-data")
    @ResponseBody
    public Map<String, Object> getBoardData(HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        Map<String, Object> response = new HashMap<>();
        Map<String, String> positions = new HashMap<>();

        for (Map.Entry<String, int[]> entry : game.getBoard().positionMap.entrySet()) {
            String key = entry.getKey();
            int[] coords = entry.getValue();
            String symbol = game.getBoard().getBoardArray()[coords[0]][coords[1]];
            if (symbol.equals("X") || symbol.equals("O")) {
                positions.put(key, symbol);
            } else {
                positions.put(key, "");
            }
        }

        response.put("positions", positions);
        response.put("currentPlayer", String.valueOf(game.getCurrentPlayer().getPieceSymbol()));
        response.put("phase", game.getGameState().name());

        return response;
    }


}
