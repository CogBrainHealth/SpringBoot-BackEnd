package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.medical.entity.MedicalChecklistEntity;

public interface MedicalChecklistRepository extends JpaRepository<MedicalChecklistEntity, Long> {
}
