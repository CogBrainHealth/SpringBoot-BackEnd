package server.brainboost.src.statistics.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "내 게임 통계 보기 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyGameStatisticsDTO {

    private int totalScore;
    private int attentionScore;
    private int SpatialPerceptionScore;
    private int MemoryScore;


}
