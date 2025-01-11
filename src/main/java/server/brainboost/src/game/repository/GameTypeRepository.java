package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.config.GameTypeName;
import server.brainboost.src.game.entity.GameTypeEntity;

import java.util.Optional;

public interface GameTypeRepository extends JpaRepository<GameTypeEntity, Long> {

    Optional<GameTypeEntity> findGameTypeEntityByGameTypeName(GameTypeName gameTypeName);

}
