package server.brainboost.src.medical.entity.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@DynamicInsert
@Table(name = "nutrient_cognitive_domain")
public class FoodCognitiveDomainEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nutrient_id", nullable = false)
    private NutrientEntity nutrient;

    @Enumerated(EnumType.STRING)
    @Column(name = "cognitive_domain", nullable = false)
    private CognitiveDomain cognitiveDomain;
}