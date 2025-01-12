package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.game.entity.TodayGameEntity;

import java.util.Optional;

public interface TodayGameRepository extends JpaRepository<TodayGameEntity, Long> {

    Optional<TodayGameEntity> findByTodayGameId(Long todayGameId);

}
