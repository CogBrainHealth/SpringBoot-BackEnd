package server.brainboost.src.home.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.game.entity.GameTypeEntity;

@Entity
@Getter
@Table(name = "today_game")
@NoArgsConstructor
@DynamicInsert
public class TodayGameEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long todayGameId;

    @Column(name="game_id")
    private Long gameId;

    @Column(nullable = false)
    private String name;

    //게임 평점 기능 추가 시 도입
   /* @Column(nullable = false)
    private double totalRating;*/

    @Column(nullable = false)
    private String description;

    //프로필 이미지
    @Column(nullable = true)
    private String imgUrl;

    @Column(nullable = false)
    private String version;

}
