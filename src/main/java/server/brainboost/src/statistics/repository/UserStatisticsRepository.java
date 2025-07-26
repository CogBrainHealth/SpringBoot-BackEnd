package server.brainboost.src.statistics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.statistics.entity.CognitiveDomainStatisticsEntity;
import server.brainboost.src.user.entity.UserEntity;

import java.util.List;
import java.util.Optional;

public interface UserStatisticsRepository extends JpaRepository<CognitiveDomainStatisticsEntity, Long> {

    @Query("select us from CognitiveDomainStatisticsEntity us inner join fetch us.user")
    List<CognitiveDomainStatisticsEntity> findCognitiveDomainStatisticsEntitiesByUser(UserEntity user);

    Optional<CognitiveDomainStatisticsEntity> findUserStatisticsEntityByUserAndCognitiveDomain(UserEntity user, CognitiveDomain cognitiveDomain);


}
