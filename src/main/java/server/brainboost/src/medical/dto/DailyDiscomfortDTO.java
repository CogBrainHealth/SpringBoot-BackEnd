package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "5번째 질문: 일상 불편함 여부 질문 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DailyDiscomfortDTO {

    //5번째 질문: 일상에 불편한 점 존재 여부
    @Schema(description = "다섯 번째 질문: 일상에 불편한 점 존재 여부-잠들기 어렵거나 자주 깸", nullable = false, example = "false")
    private Boolean isExperiencingSleepDifficulty;  // 잠들기 어렵거나 자주 깸

    @Schema(description = "다섯 번째 질문: 일상에 불편한 점 존재 여부-스트레스", nullable = false, example = "false")
    private Boolean isExperiencingStress;           // 스트레스

    @Schema(description = "다섯 번째 질문: 일상에 불편한 점 존재 여부-기억력 감퇴", nullable = false, example = "false")
    private Boolean isExperiencingMemoryLoss;       // 기억력 감퇴

    @Schema(description = "다섯 번째 질문: 일상에 불편한 점 존재 여부-예민", nullable = false, example = "false")
    private Boolean isExperiencingSensitivity;      // 예민

    @Schema(description = "다섯 번째 질문: 일상에 불편한 점 존재 여부-우울", nullable = false, example = "false")
    private Boolean isExperiencingDepression;       // 우울

}
