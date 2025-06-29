package server.brainboost.src.medical.entity.mapping;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert
@Table(name = "exercise_user_input")
public class ExerciseUserInputEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer age;

    @Column(length = 10)
    private String gender;

    private Integer mmseScore;

    @Column(columnDefinition = "json")
    private String gameResult;  // JSON 문자열 (or JsonNode 매핑도 가능)

    @Column(columnDefinition = "json")
    private String physicalInfo;  // JSON 문자열
}