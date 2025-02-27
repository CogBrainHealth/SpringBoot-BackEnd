package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.UserPregnancyEntity;

public interface UserPregnancyRepository extends JpaRepository<UserPregnancyEntity, Long> {
}
