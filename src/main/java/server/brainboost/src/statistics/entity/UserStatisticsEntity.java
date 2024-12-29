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
@Table(name = "user_statistics")
@NoArgsConstructor
@DynamicInsert
public class UserStatisticsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userStatisticsId;

    @Column(name = "total_score")
    private Long totalScore;

    @Column(name = "count")
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_type_id")
    private GameTypeEntity gameType;

    public UserStatisticsEntity(Long totalScore, Long count, UserEntity user, GameTypeEntity gameType){
        this.totalScore = totalScore;
        this.count = count;
        this.user = user;
        this.gameType = gameType;
    }

    public void updateUserStatisticEntity(Long score, Long count){
        this.totalScore += score;
        this.count += count;
    }

}
