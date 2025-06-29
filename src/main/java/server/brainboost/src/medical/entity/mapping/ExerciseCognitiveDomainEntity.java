package server.brainboost.src.medical.entity.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.exercise.ExerciseEntity;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "cognitive_domain_exercise")
public class ExerciseCognitiveDomainEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String cognitiveDomain;  // MEMORY, ATTENTION, SPATIAL_PERCEPTION, LANGUAGE

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exercise_id", nullable = false)
    private ExerciseEntity exercise;

    @Column(length = 50, nullable = false)
    private String physicalCondition;  // 관절통, 심폐취약, 정상, 낙상우려
}