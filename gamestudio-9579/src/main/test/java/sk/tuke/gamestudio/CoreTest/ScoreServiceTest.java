package sk.tuke.gamestudio.CoreTest;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreService;
import sk.tuke.gamestudio.service.ScoreServiceJPA;


import java.util.Date;
import java.util.List;

import static org.testng.AssertJUnit.*;

public class ScoreServiceTest {

    private ScoreService scoreService;

    @BeforeMethod
    public void setUp() {
        scoreService = new ScoreServiceJPA();
        scoreService.reset();
    }

    @Test
    public void testAddAndGetScore() {
        Score score = new Score("Nine Men's Morris", "TestUser", 1, new Date());
        scoreService.addScore(score);

        List<Score> scores = scoreService.getTopScores("Nine Men's Morris");
        assertFalse(scores.isEmpty());
        Score retrieved = scores.get(0);
        assertEquals(retrieved.getPlayer(), "TestUser");
        assertEquals(retrieved.getPoints(), 1);
    }

    @Test
    public void testResetScores() {
        scoreService.addScore(new Score("Nine Men's Morris", "User1", 1, new Date()));
        scoreService.reset();
        assertTrue(scoreService.getTopScores("Nine Men's Morris").isEmpty());
    }
}
