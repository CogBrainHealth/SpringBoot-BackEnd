package server.brainboost.src.game.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.enums.CognitiveDomain;

@Entity
@Getter
@Table(name = "game_type")
@NoArgsConstructor
@DynamicInsert
public class GameTypeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long gameTypeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "cognitive_domain", nullable = false)
    private CognitiveDomain cognitiveDomain;
}
