package server.brainboost.src.medical.repository.food;

import org.springframework.data.jpa.repository.JpaRepository;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.food.enums.MealPeriod;
import server.brainboost.src.medical.entity.food.MealPlanEntity;

import java.util.List;

public interface MealPlanRepository extends JpaRepository<MealPlanEntity, Long> {

    List<MealPlanEntity> findMealPlanEntitiesByCognitiveDomainAndMealPeriod(CognitiveDomain cognitiveDomain, MealPeriod mealPeriod);

}
