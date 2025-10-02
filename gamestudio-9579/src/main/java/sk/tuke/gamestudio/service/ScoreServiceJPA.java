package sk.tuke.gamestudio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.repository.ScoreRepository;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ScoreServiceJPA implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void addScore(Score score) {
        scoreRepository.save(score);
    }

    @Override
    public List<Score> getTopScores(String game) {
        return scoreRepository.findTop10ByGameOrderByPointsDesc(game);
    }

    @Override
    public void reset() {
        scoreRepository.deleteAll();
    }

    @Override
    public Score getScoreForUser(String game, String username) {
        List<Score> scores = scoreRepository.findByGameAndPlayer(game, username);
        if (scores.isEmpty()) return new Score(game, username, 0, new Date());
        return scores.get(0);
    }

}
