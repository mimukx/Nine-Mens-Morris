package sk.tuke.gamestudio.service;


import sk.tuke.gamestudio.entity.Comment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceJDBC implements CommentService {
    public static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String INSERT =
            "INSERT INTO comment (game, player, comment, commentedon) VALUES (?, ?, ?, ?)";
    private static final String SELECT =
            "SELECT game, player, comment, commentedon FROM comment WHERE game = ? ORDER BY commentedon DESC";
    private static final String DELETE =
            "DELETE FROM comment";
    @Override
    public void addComment(Comment comment) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(INSERT)) {
            ps.setString(1, comment.getGame());
            ps.setString(2, comment.getUsername());
            ps.setString(3, comment.getComment());
            ps.setTimestamp(4, new Timestamp(comment.getCommentedOn().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new CommentException("Problem inserting comment", e);
        }
    }
    @Override
    public List<Comment> getComments(String game) {
        List<Comment> comments = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(SELECT)) {
            ps.setString(1, game);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    comments.add(new Comment(
                            rs.getString("game"),
                            rs.getString("player"),
                            rs.getString("comment"),
                            rs.getTimestamp("commentedon")
                    ));
                }
            }
        } catch (SQLException e) {
            throw new CommentException("Problem retrieving comments", e);
        }
        return comments;
    }
    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(DELETE);
        } catch (SQLException e) {
            throw new CommentException("Problem resetting comments", e);
        }
    }
}