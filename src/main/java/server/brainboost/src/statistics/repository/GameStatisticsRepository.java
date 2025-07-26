package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.statistics.entity.GameStatisticsEntity;

public interface GameStatisticsRepository extends JpaRepository<GameStatisticsEntity, Long> {
}
