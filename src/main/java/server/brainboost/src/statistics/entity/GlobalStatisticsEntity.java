package server.brainboost.src.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.user.entity.UserEntity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_type_id")
    private GameTypeEntity gameType;

    @Version
    private Long OLock;  // 'version' 대신 'lock' 사용


    public GlobalStatisticsEntity(Long totalScore, Long count, GameTypeEntity gameType){
        this.totalScore = totalScore;
        this.count = count;
        this.gameType = gameType;
    }

    public void updateGlobalStatisticsEntity(Long score, Long count){
        this.totalScore += score;
        this.count += count;
    }

}
