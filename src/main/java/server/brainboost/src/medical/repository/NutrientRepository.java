package server.brainboost.src.medical.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

public interface NutrientRepository extends JpaRepository<NutrientEntity, Long> {

	Optional<NutrientEntity> findNutrientEntityById(Long nutrientId);
}
