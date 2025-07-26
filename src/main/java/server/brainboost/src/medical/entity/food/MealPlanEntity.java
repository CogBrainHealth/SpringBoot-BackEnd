package server.brainboost.src.medical.entity.food;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.food.enums.MealPeriod;

@Entity
@Getter
@Table(name = "meal_plan")
@NoArgsConstructor
@DynamicInsert
public class MealPlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long mealPlanId;

    @Column(nullable = false)
    private String foodName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "meal_period")
    private MealPeriod mealPeriod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cognitive_domain")
    private CognitiveDomain cognitiveDomain;

}
