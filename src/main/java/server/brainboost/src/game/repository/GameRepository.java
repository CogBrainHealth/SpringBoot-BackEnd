package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.brainboost.config.Status;
import server.brainboost.src.game.dto.GameInterface;
import server.brainboost.src.game.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {


    @Query("select g from GameEntity g inner join fetch g.gameType where g.status =:status and g.gameType.status =:status")
    List<GameEntity> findGames(@Param("status")Status status);

    List<GameEntity> findGameEntitiesByStatus(Status status);

    @Query(value = "Select id, name, img_url, description, version \n" +
            "From game\n" +
            "Order by rand()\n" +
            "Limit 1", nativeQuery = true)
    Optional<GameInterface> findGameInterfaceOrderByRandom();

}
