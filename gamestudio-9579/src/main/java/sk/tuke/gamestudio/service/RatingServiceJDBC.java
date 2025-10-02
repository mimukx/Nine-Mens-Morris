package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class RatingServiceJDBC implements RatingService {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    private static final String INSERT_OR_UPDATE =
            "INSERT INTO rating (game, player, rating, ratedon) VALUES (?, ?, ?, ?) " +
                    "ON CONFLICT (game, player) DO UPDATE SET rating = EXCLUDED.rating, ratedon = EXCLUDED.ratedon";

    private static final String SELECT_AVG =
            "SELECT AVG(rating) FROM rating WHERE game = ?";

    private static final String SELECT_PLAYER =
            "SELECT rating FROM rating WHERE game = ? AND player = ?";

    private static final String DELETE =
            "DELETE FROM rating";

    @Override
    public void setRating(Rating rating) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            try (PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(*) FROM rating WHERE game = ? AND player = ?")) {
                ps.setString(1, rating.getGame());
                ps.setString(2, rating.getPlayer());
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    int count = rs.getInt(1);

                    if (count > 0) {
                        // обновление существующего рейтинга
                        try (PreparedStatement update = connection.prepareStatement(
                                "UPDATE rating SET rating = ?, ratedon = ? WHERE game = ? AND player = ?")) {
                            update.setInt(1, rating.getRating());
                            update.setTimestamp(2, new Timestamp(rating.getRatedOn().getTime()));
                            update.setString(3, rating.getGame());
                            update.setString(4, rating.getPlayer());
                            update.executeUpdate();
                        }
                    } else {
                        // вставка нового рейтинга
                        try (PreparedStatement insert = connection.prepareStatement(
                                "INSERT INTO rating (game, player, rating, ratedon) VALUES (?, ?, ?, ?)")) {
                            insert.setString(1, rating.getGame());
                            insert.setString(2, rating.getPlayer());
                            insert.setInt(3, rating.getRating());
                            insert.setTimestamp(4, new Timestamp(rating.getRatedOn().getTime()));
                            insert.executeUpdate();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            throw new RatingException("Problem saving rating", e);
        }
    }


    @Override
    public int getAverageRating(String game) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT AVG(rating) FROM rating WHERE game = ?")) {

            ps.setString(1, game);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                else return 0;
            }

        } catch (SQLException e) {
            throw new RatingException("Problem getting average rating", e);
        }
    }


    @Override
    public int getRating(String game, String player) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT rating FROM rating WHERE game = ? AND player = ?")) {

            ps.setString(1, game);
            ps.setString(2, player);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getInt(1);
                else return 0;
            }

        } catch (SQLException e) {
            throw new RatingException("Problem getting rating", e);
        }
    }


    @Override
    public void reset() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {

            statement.executeUpdate("DELETE FROM rating");

        } catch (SQLException e) {
            throw new RatingException("Problem resetting ratings", e);
        }
    }

    @Override
    public List<Rating> getRatings(String game) {
        return List.of();
    }

    @Override
    public Optional<Integer> getRatingForUser(String game, String username) {
        return Optional.empty();
    }

    @Override
    public List<Rating> getAverageRatings(String game) {
        return List.of();
    }

}

