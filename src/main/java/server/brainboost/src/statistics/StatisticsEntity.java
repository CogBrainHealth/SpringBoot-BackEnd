package server.brainboost.src.statistics;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.game.entity.GameTypeEntity;

@Entity
@Getter
@Setter
@Table(name = "statistics")
@NoArgsConstructor
@DynamicInsert
public class StatisticsEntity extends BaseEntity {

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

}
