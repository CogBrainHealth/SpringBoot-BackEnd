package server.brainboost.src.medical.entity.food;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@Setter
@Table(name = "food")
@NoArgsConstructor
@DynamicInsert
public class MealPlanEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long mealPlanId;


}
