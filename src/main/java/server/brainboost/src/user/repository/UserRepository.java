package server.brainboost.src.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import server.brainboost.config.Status;
import server.brainboost.src.user.entity.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findUserEntityByUsername(String username);
Optional<UserEntity> findUserEntityByUsernameAndStatus(String username, Status status);

    Optional<UserEntity> findUserEntityByUserIdAndStatus(Long userId, Status status);

    @Query("select u from UserEntity as u LEFT join FETCH u.medicalChecklist where u.userId =:userId")
    Optional<UserEntity> findUserEntityLeftJoinMedicalChecklistByUserId(@Param("userId")Long userId);

    boolean existsAllByUsername(String username);
}
