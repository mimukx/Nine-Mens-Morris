package sk.tuke.gamestudio.CoreTest;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJPA;


import java.util.Date;

import static org.testng.AssertJUnit.assertEquals;

public class RatingServiceTest {

    private RatingService ratingService;

    @BeforeMethod
    public void setUp() {
        ratingService = new RatingServiceJPA();
        ratingService.reset();
    }

    @Test
    public void testSetAndGetRating() {
        Rating rating = new Rating("Nine Men's Morris", "TestUser", 4, new Date());
        ratingService.setRating(rating);

        int result = ratingService.getRating("Nine Men's Morris", "TestUser");
        assertEquals(result, 4);
    }

    @Test
    public void testGetAverageRating() {
        ratingService.setRating(new Rating("Nine Men's Morris", "User1", 3, new Date()));
        ratingService.setRating(new Rating("Nine Men's Morris", "User2", 5, new Date()));
        assertEquals(ratingService.getAverageRating("Nine Men's Morris"), 4);
    }

    @Test
    public void testResetRatings() {
        ratingService.setRating(new Rating("Nine Men's Morris", "User1", 3, new Date()));
        ratingService.reset();
        assertEquals(ratingService.getAverageRating("Nine Men's Morris"), 0);
    }
}
