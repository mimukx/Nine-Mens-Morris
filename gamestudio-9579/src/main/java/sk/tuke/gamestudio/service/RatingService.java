package sk.tuke.gamestudio.service;




import sk.tuke.gamestudio.entity.Rating;

import java.util.List;
import java.util.Optional;

public interface RatingService {
    void setRating(Rating rating) throws RatingException;
    int getAverageRating(String game) throws RatingException;
    int getRating(String game, String player) throws RatingException;
    void reset() throws RatingException;
    List<Rating> getRatings(String game);
    Optional<Integer> getRatingForUser(String game, String username);
    List<Rating> getAverageRatings(String game);
}
