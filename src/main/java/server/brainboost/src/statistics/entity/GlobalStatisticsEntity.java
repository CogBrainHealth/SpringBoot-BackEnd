package server.brainboost.src.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.enums.CognitiveDomain;

@Entity
@Getter
@Setter
@Table(name = "global_statistics")
@NoArgsConstructor
@DynamicInsert
public class GlobalStatisticsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long statisticsId;

    @Column(name = "total_score")
    private Long totalScore;

    @Column(name = "count")
    private Long count;

    @Enumerated(EnumType.STRING)
    private CognitiveDomain cognitiveDomain;

    @Version
    private Long OLock;  // 'version' 대신 'lock' 사용


    public GlobalStatisticsEntity(Long totalScore, Long count, CognitiveDomain cognitiveDomain){
        this.totalScore = totalScore;
        this.count = count;
        this.cognitiveDomain = cognitiveDomain;
    }

    public void updateGlobalStatisticsEntity(Long score, Long count){
        this.totalScore += score;
        this.count += count;
    }

}
