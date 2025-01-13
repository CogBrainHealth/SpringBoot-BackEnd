package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.UserConditionEntity;

public interface UserConditionRepository extends JpaRepository<UserConditionEntity, Long> {
}
