package server.brainboost.src.medical.entity.nutrientCombinations;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

@Entity
@Getter
@Table(name = "nutrient_combinations")
@NoArgsConstructor
@DynamicInsert
public class NutrientCombinationsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private Integer groupNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrient_id")
    private NutrientEntity nutrientEntity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "cognitive_domain")
    private CognitiveDomain cognitiveDomain;

}
