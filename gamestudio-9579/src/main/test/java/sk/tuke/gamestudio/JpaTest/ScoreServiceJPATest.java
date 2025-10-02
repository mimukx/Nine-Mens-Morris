package sk.tuke.gamestudio.JpaTest;


import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceJPA;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Date;
import java.util.List;

import static org.testng.AssertJUnit.*;


@SpringBootTest
@TestPropertySource(properties = {"spring.profiles.active=test"})
@Transactional
public class ScoreServiceJPATest {

    @PersistenceContext
    private EntityManager entityManager;

    private ScoreServiceJPA scoreService;

    @BeforeEach
    void setUp() {
        scoreService = new ScoreServiceJPA();
        injectEntityManager(scoreService, entityManager);
    }

    private void injectEntityManager(ScoreServiceJPA service, EntityManager em) {
        try {
            var field = ScoreServiceJPA.class.getDeclaredField("entityManager");
            field.setAccessible(true);
            field.set(service, em);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAddAndFindScore() {
        // Arrange
        Score score = new Score("Nine Men's Morris", "Bob", 90, new Date());
        scoreService.addScore(score);

        // Act
        List<Score> allScores = scoreService.getTopScores("Nine Men's Morris");

        // Assert
        assertEquals(1, allScores.size());
        Score result = allScores.get(0);
        assertEquals("Bob", result.getPlayer());
        assertEquals(90, result.getPoints());
        assertEquals("Nine Men's Morris", result.getGame());
    }

    @Test
    public void testResetScores() {
        scoreService.addScore(new Score("Nine Men's Morris", "Player1", 50, new Date()));
        scoreService.addScore(new Score("Nine Men's Morris", "Player2", 60, new Date()));

        assertFalse(scoreService.getTopScores("Nine Men's Morris").isEmpty());

        scoreService.reset();

        assertTrue(scoreService.getTopScores("Nine Men's Morris").isEmpty());
    }
}
