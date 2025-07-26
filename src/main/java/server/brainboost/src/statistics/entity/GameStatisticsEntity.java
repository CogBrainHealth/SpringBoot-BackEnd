package server.brainboost.src.statistics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.entity.UserRecordEntity;

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

    //record_key의 값이 자주 변하지 않는다고 판단해 정규화 x
    @Column(name = "record_key")
    private String recordKey;

    @Column(name = "record_value")
    private String recordValue;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private GameEntity game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_record_id")
    private UserRecordEntity userRecord;



}
