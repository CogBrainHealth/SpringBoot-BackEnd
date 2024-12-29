package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import server.brainboost.config.TypeName;

@Getter
@Setter
@Schema(description = "게임 기본정보 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDetailsDTO {

    private Long gameId;
    private String gameName;
    private String gameImgUrl;
    private String gameDescription;
    private TypeName gameTypeName;


}
