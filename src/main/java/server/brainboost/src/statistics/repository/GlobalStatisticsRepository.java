package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.statistics.entity.GlobalStatisticsEntity;

import java.util.Optional;

public interface GlobalStatisticsRepository extends JpaRepository<GlobalStatisticsEntity, Long> {

    Optional<GlobalStatisticsEntity> findStatisticsEntityByGameType(GameTypeEntity gameType);


}
