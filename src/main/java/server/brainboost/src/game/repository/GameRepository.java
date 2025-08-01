package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import server.brainboost.config.Status;
import server.brainboost.src.game.dto.inter.GameInterface;
import server.brainboost.src.game.entity.GameEntity;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends JpaRepository<GameEntity, Long> {


    @Query("select g from GameEntity g where g.status =:status")
    List<GameEntity> findGames(@Param("status")Status status);

    Optional<GameEntity> findGameEntityByName(String name);

    Optional<GameEntity> findGameEntityByGameId(Long id);

    @Query(value = "Select id, name, img_url, description, version \n" +
            "From game\n" +
            "Order by rand()\n" +
            "Limit 1", nativeQuery = true)
    Optional<GameInterface> findGameInterfaceOrderByRandom();

}
