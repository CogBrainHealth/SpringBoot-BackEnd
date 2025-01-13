package server.brainboost.src.medical.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.NutrientEntity;

public interface NutrientRepository extends JpaRepository<NutrientEntity, Long> {
}
