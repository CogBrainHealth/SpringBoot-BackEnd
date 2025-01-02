package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "1번째 질문: 출산/임신 관련 상태 질문 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReproductiveHealthDTO {

    // 1번째 질문: 해당 사항
    @Schema(description = "첫 번째 질문: 임신 여부", nullable = true, example = "true")
    private Boolean isPregnant;    // 임신 중

    @Schema(description = "첫 번째 질문: 수유 여부", nullable = true, example = "false")
    private Boolean isBreastfeeding; // 수유 중

    @Schema(description = "첫 번째 질문: 자녀 계획 여부 여부", nullable = true, example = "true")
    private Boolean isPlanningChild; // 자녀 계획 여부

    @Schema(description = "첫 번째 질문: 폐경 여부", nullable = true, example = "false")
    private Boolean isMenopause;     // 폐경 여부


}
