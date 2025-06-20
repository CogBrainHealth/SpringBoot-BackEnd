package server.brainboost.src.game.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import server.brainboost.enums.CognitiveDomain;

import java.util.ArrayList;
import java.util.List;

public class GameResponseDTO {


    @Getter
    @Setter
    @Schema(description = "각 영역별 모든 게임 리스트 DTO")
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameByCognitionDTO {

        @Schema(description = "주의력 영역에 해당하는 게임 리스트")
        private List<GameDetailsDTO> attentionGameList = new ArrayList<>();

        @Schema(description = "공간지각능력 영역에 해당하는 게임 리스트")
        private List<GameDetailsDTO> spatialPerceptionGameList = new ArrayList<>();

        @Schema(description = "기억력 영역에 해당하는 게임 리스트")
        private List<GameDetailsDTO> memoryGameList = new ArrayList<>();

    }

    @Getter
    @Setter
    @Schema(description = "게임 기본정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameDetailsDTO {

        private Long gameId;
        private String gameName;
        private String gameImgUrl;
        private String gameDescription;
        private String gameVersion;
        private CognitiveDomain cognitiveDomain;


    }
}
