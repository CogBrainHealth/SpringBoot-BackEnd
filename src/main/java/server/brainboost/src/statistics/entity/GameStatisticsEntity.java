package server.brainboost.src.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.statistics.entity.enums.AgeGroup;
import server.brainboost.src.user.entity.UserEntity;

@Entity
@Getter
@Table(name = "game_statistics")
@NoArgsConstructor
@DynamicInsert
public class GameStatisticsEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_record_id")
    private Long gameRecordId;

    @Enumerated(EnumType.STRING)
    private AgeGroup ageGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    public GameStatisticsEntity(AgeGroup ageGroup, UserEntity user, GameEntity game) {
        this.ageGroup = ageGroup;
        this.user = user;
        this.game = game;
    }

}
