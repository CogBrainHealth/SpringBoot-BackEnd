package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.statistics.entity.GameStatisticsEntity;
import server.brainboost.src.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface GameStatisticsRepository extends JpaRepository<GameStatisticsEntity, Long> {

    List<GameStatisticsEntity> findGameStatisticsEntitiesByUserAndGame(UserEntity user, GameEntity game);

    Optional<GameStatisticsEntity> findTopByUserAndGameOrderByCreateAtDesc(UserEntity user, GameEntity game);

}
