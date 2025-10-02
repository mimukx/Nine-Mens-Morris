package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.repository.RatingRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RatingServiceJPA implements RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Override
    public void setRating(Rating rating) {
        ratingRepository.save(rating);
    }

    @Override
    public int getAverageRating(String game) {
        Double avg = ratingRepository.findAverageRatingByGame(game);
        return avg != null ? avg.intValue() : 0;
    }

    @Override
    public int getRating(String game, String player) {
        return ratingRepository.findRatingByGameAndUsername(game, player)
                .map(Rating::getRating)
                .orElse(0);
    }

    @Override
    public void reset() {
        ratingRepository.deleteAll();
    }

    @Override
    public List<Rating> getRatings(String game) {
        return ratingRepository.findByGame(game);
    }

    @Override
    public List<Rating> getAverageRatings(String game) {
        return ratingRepository.findAverageRatings(game);
    }

    @Override
    public Optional<Integer> getRatingForUser(String game, String username) {
        return ratingRepository.findRatingByGameAndUsername(game, username).map(Rating::getRating);
    }


}
