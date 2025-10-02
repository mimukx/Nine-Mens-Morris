package sk.tuke.gamestudio.server.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;
import sk.tuke.gamestudio.core.Game;


@Controller
@Scope(WebApplicationContext.SCOPE_SESSION)
@RequestMapping("/morris")
public class NineMensMorrisController {

    private Game game = new Game(false); // false: без бота

    @GetMapping
    public String morris(Model model) {
        model.addAttribute("board", game.getBoardAsString());
        return "morris";
    }

    @GetMapping(params = "command")
    public String processCommand(@RequestParam String command, Model model) {
        game.processCommand(command);
        model.addAttribute("board", game.getBoardAsString());
        return "morris";
    }
}


