package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.UserDiscomfortEntity;

public interface UserDiscomfortRepository extends JpaRepository<UserDiscomfortEntity, Long> {
}
