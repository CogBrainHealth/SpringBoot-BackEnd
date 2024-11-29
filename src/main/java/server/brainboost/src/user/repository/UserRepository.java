package server.brainboost.src.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
}
