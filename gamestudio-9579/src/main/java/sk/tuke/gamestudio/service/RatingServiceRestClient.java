package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;

import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceRestClient implements RatingService {
    private static final String URL = "http://localhost:8080/api/rating";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void setRating(Rating rating) {
        restTemplate.postForEntity(URL, rating, Rating.class);
    }

    @Override
    public int getAverageRating(String game) {
        return restTemplate.getForObject(URL + "/" + game, Integer.class);
    }

    @Override
    public int getRating(String game, String player) {
        return restTemplate.getForObject(URL + "/" + game + "/" + player, Integer.class);
    }

    @Override
    public void reset() {
        throw new UnsupportedOperationException("Not supported via web service");
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

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
