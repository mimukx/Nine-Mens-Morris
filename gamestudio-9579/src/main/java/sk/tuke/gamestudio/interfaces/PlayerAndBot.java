package sk.tuke.gamestudio.interfaces;


import sk.tuke.gamestudio.core.Game;
import sk.tuke.gamestudio.core.Move;

public interface PlayerAndBot {
    String getName();
    char getSymbol();
    Move makeMove(Game game);
}
