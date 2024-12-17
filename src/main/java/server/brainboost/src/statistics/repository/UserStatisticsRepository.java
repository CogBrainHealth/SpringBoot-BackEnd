package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.brainboost.src.statistics.entity.UserStatisticsEntity;
import server.brainboost.src.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<UserStatisticsEntity, Long> {

    @Query("select us from UserStatisticsEntity us inner join fetch us.user")
    List<UserStatisticsEntity> findUserStatisticsEntitiesByUser(UserEntity user);




}
