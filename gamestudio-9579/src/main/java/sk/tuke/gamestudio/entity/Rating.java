package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Rating.getAverageRating", query = "SELECT AVG(r.rating) FROM Rating r WHERE r.game = :game")
@NamedQuery(name = "Rating.resetRatings", query = "DELETE FROM Rating")
public class Rating implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String username;
    private int rating;
    private Date ratedOn;

    public Rating() {}

    public Rating(String game, String username, int rating, Date ratedOn) {
        this.game = game;
        this.username = username;
        this.rating = rating;
        this.ratedOn = ratedOn;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }

    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }

    public String getPlayer() { return username; }
    public void setPlayer(String username) { this.username = username; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public Date getRatedOn() { return ratedOn; }
    public void setRatedOn(Date ratedOn) { this.ratedOn = ratedOn; }

    public String getUsername() {
        return username;
    }

}
