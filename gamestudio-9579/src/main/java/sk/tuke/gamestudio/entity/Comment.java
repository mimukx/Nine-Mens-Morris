package sk.tuke.gamestudio.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQuery(name = "Comment.getComments", query = "SELECT c FROM Comment c WHERE c.game = :game ORDER BY c.commentedOn DESC")
@NamedQuery(name = "Comment.resetComments", query = "DELETE FROM Comment")
public class Comment implements Serializable {

    @Id
    @GeneratedValue
    private int ident;

    private String game;
    private String username;
    @Column(name = "content")
    private String comment;
    private Date commentedOn;

    public Comment() {}

    public Comment(String game, String username, String comment, Date commentedOn) {
        this.game = game;
        this.username = username;
        this.comment = comment;
        this.commentedOn = commentedOn;
    }

    public int getIdent() { return ident; }
    public void setIdent(int ident) { this.ident = ident; }

    public String getGame() { return game; }
    public void setGame(String game) { this.game = game; }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Date getCommentedOn() { return commentedOn; }
    public void setCommentedOn(Date commentedOn) { this.commentedOn = commentedOn; }
}
