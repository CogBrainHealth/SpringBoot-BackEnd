package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.brainboost.config.Status;
import server.brainboost.src.game.entity.GameEntity;

import java.util.List;

public interface GameRepository extends JpaRepository<GameEntity, Long> {


    @Query("select g from GameEntity g inner join fetch g.gameType where g.status =:status and g.gameType.status =:status")
    List<GameEntity> findGames(@Param("status")Status status);

    List<GameEntity> findGameEntitiesByStatus(Status status);


}
