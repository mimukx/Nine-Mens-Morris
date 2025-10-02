package sk.tuke.gamestudio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.tuke.gamestudio.entity.Rating;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.game = ?1")
    Double findAverageRatingByGame(String game);
    List<Rating> findByGame(String game);

    @Query("SELECT new sk.tuke.gamestudio.entity.Rating(r.game, r.username, r.rating, r.ratedOn) FROM Rating r WHERE r.game = :game ORDER BY r.username")
    List<Rating> findAverageRatings(@Param("game") String game);
    Optional<Rating> findRatingByGameAndUsername(String game, String username);
    List<Rating> findAllByGameOrderByRatedOnDesc(String game);
}
