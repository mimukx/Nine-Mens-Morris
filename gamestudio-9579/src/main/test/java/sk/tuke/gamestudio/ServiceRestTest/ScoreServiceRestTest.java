package sk.tuke.gamestudio.ServiceRestTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.server.webservice.ScoreServiceRest;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
@Transactional
public class ScoreServiceRestTest {

    @PersistenceContext
    private ScoreServiceRest scoreServiceRest;

    @Test
    public void testAddAndGetScore() {
        Score score = new Score("nine_mens_morris", "RestPlayer", 100, new Date());
        scoreServiceRest.addScore(score);

        List<Score> scores = scoreServiceRest.getTopScores("nine_mens_morris");
        assertFalse(scores.isEmpty(), "Score list should not be empty");
        assertEquals("RestPlayer", scores.get(0).getPlayer());
    }
}

