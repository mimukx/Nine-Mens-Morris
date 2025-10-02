package sk.tuke.gamestudio.JpaTest;

import org.springframework.test.context.TestPropertySource;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;

import static org.junit.Assert.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
@Transactional
public class RatingServiceJPATest {

    @PersistenceContext
    private RatingService ratingService;

    @Test
    public void testSetAndGetRating() {
        Rating rating = new Rating("Nine Men's Morris", "Tester", 4, new Date());
        ratingService.setRating(rating);

        int value = ratingService.getAverageRating("Nine Men's Morris");
        assertEquals(4, value);
    }

    @Test
    public void testResetRatings() {
        ratingService.setRating(new Rating("Nine Men's Morris", "Tester", 5, new Date()));
        ratingService.reset();
        int avg = ratingService.getAverageRating("Nine Men's Morris");
        assertEquals(0, avg);
    }
}

