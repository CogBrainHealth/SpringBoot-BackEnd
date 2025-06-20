package server.brainboost.src.game.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@Table(name = "today_game", uniqueConstraints = @UniqueConstraint(columnNames = "date"))
@NoArgsConstructor
@DynamicInsert
public class TodayGameEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long todayGameId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;


    public TodayGameEntity(LocalDate date, GameEntity game){
        this.date = date;
        this.game = game;
    }


}

