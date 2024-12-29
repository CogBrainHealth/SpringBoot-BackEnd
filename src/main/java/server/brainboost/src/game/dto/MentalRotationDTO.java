package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "지도보고 길찾기 게임 후, 프론트 단에서 서버쪽에 넘길 데이터")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MentalRotationDTO {

    private Integer score;

    //TODO: row data도 받을 수 있으면 받기

}
