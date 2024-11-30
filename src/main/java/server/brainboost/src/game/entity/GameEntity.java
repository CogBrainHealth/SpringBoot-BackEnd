package server.brainboost.src.game.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

@Entity
@Getter
@Table(name = "game")
@NoArgsConstructor
@DynamicInsert
public class GameEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_type_id")
    private GameTypeEntity gameType;



}
