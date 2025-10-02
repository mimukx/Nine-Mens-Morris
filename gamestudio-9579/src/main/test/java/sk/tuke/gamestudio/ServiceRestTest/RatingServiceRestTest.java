package sk.tuke.gamestudio.ServiceRestTest;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.server.webservice.RatingServiceRest;

import javax.persistence.PersistenceContext;

import java.util.Date;

import static org.testng.AssertJUnit.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingServiceRestTest {

    @PersistenceContext
    private RatingServiceRest ratingServiceRest;

    @Test
    public void testSetAndGetRating() {
        Rating rating = new Rating("nine_mens_morris", "RestPlayer", 5, new Date());
        ratingServiceRest.setRating(rating);

        int ratingValue = ratingServiceRest.getAverageRating("nine_mens_morris");
        assertTrue(ratingValue >= 1 && ratingValue <= 5);
    }
}

