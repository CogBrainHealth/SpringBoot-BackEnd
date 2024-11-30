package server.brainboost.src.game.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;

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

    @Column(name = "type_name", nullable = false)
    private String TypeName;


}
