package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import server.brainboost.src.game.entity.TodayGameEntity;

import java.time.LocalDate;
import java.util.Optional;

public interface TodayGameRepository extends JpaRepository<TodayGameEntity, Long> {

    Optional<TodayGameEntity> findByTodayGameId(Long todayGameId);

    Optional<TodayGameEntity> findTopByOrderByCreateAtAsc();

}
