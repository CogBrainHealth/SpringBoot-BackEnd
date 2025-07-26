package server.brainboost.src.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.user.entity.UserEntity;

@Entity
@Getter
@Setter
@Table(name = "cognitive_domain_statistics")
@NoArgsConstructor
@DynamicInsert
public class CognitiveDomainStatisticsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long cognitiveDomainStatisticsId;

    @Column(name = "total_score")
    private Long totalScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING)
    private CognitiveDomain cognitiveDomain;

    public CognitiveDomainStatisticsEntity(Long totalScore, Long count, UserEntity user, CognitiveDomain cognitiveDomain) {
        this.totalScore = totalScore;
        this.count = count;
        this.user = user;
        this.cognitiveDomain = cognitiveDomain;
    }

    public void updateCategoryScoreEntity(Long score, Long count){
        this.totalScore += score;
        this.count += count;
    }

}
