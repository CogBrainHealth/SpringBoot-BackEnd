package server.brainboost.src.game.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.game.entity.GameTypeEntity;

import java.util.Optional;

public interface GameTypeRepository extends JpaRepository<GameTypeEntity, Long> {

    Optional<GameTypeEntity> findGameTypeEntityByCognitiveDomain(CognitiveDomain cognitiveDomain);

}
