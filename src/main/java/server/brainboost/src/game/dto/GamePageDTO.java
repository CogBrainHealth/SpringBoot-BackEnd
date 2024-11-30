package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "훈련 기본화면 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GamePageDTO {

    private List<GameDetailsDTO> typeAList = new ArrayList<>();
    private List<GameDetailsDTO> typeBList = new ArrayList<>();
    private List<GameDetailsDTO> typeCList = new ArrayList<>();



}
