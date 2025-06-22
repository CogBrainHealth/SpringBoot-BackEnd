package server.brainboost.src.medical.entity.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.food.FoodEntity;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "food_nutrient")
public class FoodNutrientEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id", nullable = false)
    private FoodEntity food;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrient_id", nullable = false)
    private NutrientEntity nutrient;

}

