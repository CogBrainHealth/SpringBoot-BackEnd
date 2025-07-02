package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.statistics.entity.CategoryScoreEntity;
import server.brainboost.src.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<CategoryScoreEntity, Long> {

    @Query("select us from CategoryScoreEntity us inner join fetch us.user")
    List<CategoryScoreEntity> findUserStatisticsEntitiesByUser(UserEntity user);

    Optional<CategoryScoreEntity> findUserStatisticsEntityByUserAndGameType(UserEntity user, GameTypeEntity gameType);


}
