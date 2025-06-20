package server.brainboost.src.statistics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

public class StatisticResponse {

    @Getter
    @Setter
    @Schema(description = "게임 통계 정보 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class GameStatisticsDTO {

        @Schema(description = "전체 점수")
        private int totalScore;

        @Schema(description = "주의력 영역 점수")
        private int attentionScore;

        @Schema(description = "공간지각능력 점수")
        private int SpatialPerceptionScore;

        @Schema(description = "기억력 영역 점수")
        private int MemoryScore;


    }
}
