package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.UserMedicineEntity;

public interface UserMedicineRepository extends JpaRepository<UserMedicineEntity, Long> {
}
