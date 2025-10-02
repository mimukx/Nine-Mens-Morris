package sk.tuke.gamestudio.ServiceRestClientTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingServiceRestClient;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingServiceRestClientTest {

    private RestTemplate restTemplate;
    private RatingServiceRestClient ratingService;

    private final String baseUrl = "http://localhost:8080/api/rating";

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        ratingService = new RatingServiceRestClient();
        ratingService.setRestTemplate(restTemplate);
    }

    @Test
    void testSetRating() {
        Rating rating = new Rating("nine_mens_morris", "Tester", 5, new Date());
        doNothing().when(restTemplate).postForEntity(baseUrl, rating, Rating.class);

        assertDoesNotThrow(() -> ratingService.setRating(rating));
        verify(restTemplate).postForEntity(baseUrl, rating, Rating.class);
    }

    @Test
    void testGetAverageRating() {
        int mockAverage = 4;
        when(restTemplate.getForEntity(baseUrl + "/nine_mens_morris", Integer.class))
                .thenReturn(new ResponseEntity<>(mockAverage, HttpStatus.OK));

        int result = ratingService.getAverageRating("nine_mens_morris");
        assertEquals(4, result);
    }

    @Test
    void testGetRatingForPlayer() {
        int mockRating = 5;
        when(restTemplate.getForEntity(baseUrl + "/nine_mens_morris/Tester", Integer.class))
                .thenReturn(new ResponseEntity<>(mockRating, HttpStatus.OK));

        int result = ratingService.getRating("nine_mens_morris", "Tester");
        assertEquals(5, result);
    }

    @Test
    void testResetNotSupported() {
        assertThrows(UnsupportedOperationException.class, () -> ratingService.reset());
    }
}

