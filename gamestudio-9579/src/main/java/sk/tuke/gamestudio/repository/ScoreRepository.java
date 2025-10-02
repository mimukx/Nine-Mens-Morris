package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Score;

import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
    List<Score> findTop10ByGameOrderByPointsDesc(String game);
    List<Score> findByGameAndPlayer(String game, String player);
}
