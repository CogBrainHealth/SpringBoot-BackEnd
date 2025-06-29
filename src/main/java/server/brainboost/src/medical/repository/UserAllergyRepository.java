package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.userStatus.UserAllergyEntity;

public interface UserAllergyRepository extends JpaRepository<UserAllergyEntity, Long> {
}
