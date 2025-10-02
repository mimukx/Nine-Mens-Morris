package sk.tuke.gamestudio.ServiceRestClientTest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.service.ScoreServiceRestClient;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScoreServiceRestClientTest {
    private RestTemplate restTemplate;
    private ScoreServiceRestClient scoreService;

    @BeforeEach
    public void setup() {
        restTemplate = mock(RestTemplate.class);
        scoreService = new ScoreServiceRestClient();
        scoreService.setRestTemplate(restTemplate);
    }

    @Test
    public void testGetTopScores() {
        String game = "nine_mens_morris";
        Score[] expectedScores = {
                new Score(game, "Tester1", 100, new Date()),
                new Score(game, "Tester2", 80, new Date())
        };

        when(restTemplate.getForEntity(anyString(), eq(Score[].class)))
                .thenReturn(new org.springframework.http.ResponseEntity<>(expectedScores, org.springframework.http.HttpStatus.OK));

        List<Score> scores = scoreService.getTopScores(game);

        assertNotNull(scores);
        assertEquals(2, scores.size());
        assertEquals("Tester1", scores.get(0).getPlayer());
    }

    @Test
    public void testAddScore() {
        Score score = new Score("nine_mens_morris", "PlayerX", 50, new Date());
        scoreService.addScore(score);
        verify(restTemplate).postForEntity(anyString(), eq(score), eq(Score.class));
    }
}

