package server.brainboost.src.medical.repository.userStatus;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.userStatus.UserMedicineEntity;

public interface UserMedicineRepository extends JpaRepository<UserMedicineEntity, Long> {
}
