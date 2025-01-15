package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Schema(description = "훈련 기본화면 DTO")
@AllArgsConstructor
@NoArgsConstructor
public class GamePageDTO {

    @Schema(description = "주의력 영역에 해당하는 게임 리스트")
    private List<GameDetailsDTO> attentionGameList = new ArrayList<>();

    @Schema(description = "공간지각능력 영역에 해당하는 게임 리스트")
    private List<GameDetailsDTO> spatialPerceptionGameList = new ArrayList<>();

    @Schema(description = "기억력 영역에 해당하는 게임 리스트")
    private List<GameDetailsDTO> memoryGameList = new ArrayList<>();



}
