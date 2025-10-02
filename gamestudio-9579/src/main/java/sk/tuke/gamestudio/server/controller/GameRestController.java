package sk.tuke.gamestudio.server.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sk.tuke.gamestudio.bot.BotPlayer;
import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.enums.GameState;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class GameRestController {

    @GetMapping("/state")
    public Map<String, Object> getGameState(HttpSession session) {
        Game game = getGame(session);
        Map<String, Object> response = new HashMap<>();

        Map<String, String> positions = new HashMap<>();
        for (Map.Entry<String, int[]> entry : game.getBoard().positionMap.entrySet()) {
            String pos = entry.getKey();
            int[] coords = entry.getValue();
            String symbol = game.getBoard().getSymbolAt(coords[0], coords[1]);
            if ("X".equals(symbol) || "O".equals(symbol))
                positions.put(pos, symbol);
        }

        response.put("positions", positions);
        response.put("currentPlayer", game.getCurrentPlayer().getPieceSymbol());
        response.put("phase", game.getGameState().name());

        return response;
    }

    @PostMapping("/move")
    public void makeMove(@RequestParam String position, HttpSession session) {
        Game game = getGame(session);
        if (game.getGameState() != GameState.GAME_OVER) {
            game.processCommand(position);

            if (game.getCurrentPlayer() instanceof BotPlayer &&
                    game.getGameState() != GameState.GAME_OVER) {
                ((BotPlayer) game.getCurrentPlayer()).makeMove(game);
            }
        }
    }


    @PostMapping("/reset")
    public void resetGame(HttpSession session) {
        session.setAttribute("game", new Game(false));
    }

    private Game getGame(HttpSession session) {
        Game game = (Game) session.getAttribute("game");
        if (game == null) {
            game = new Game(false);
            session.setAttribute("game", game);
        }
        return game;
    }

}
