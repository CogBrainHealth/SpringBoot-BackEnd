package server.brainboost.src.medical.repository.nutrientCombinations;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.nutrientCombinations.NutrientCombinationsEntity;

import java.util.List;

public interface NutrientCombinationsRepository extends JpaRepository<NutrientCombinationsEntity, Long> {

    List<NutrientCombinationsEntity> findNutrientCombinationsEntitiesByCognitiveDomainAndGroupNumber(CognitiveDomain cognitiveDomain, Integer GroupNumber);
}
