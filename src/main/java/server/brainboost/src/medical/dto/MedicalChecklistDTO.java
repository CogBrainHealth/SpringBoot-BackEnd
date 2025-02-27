package server.brainboost.src.medical.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "개인 정보 건강 리스트 정보를 가져올 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicalChecklistDTO {

    @Schema(description = "1번째 질문: 출산/임신 관련 상태 질문 DTO, 사용자가 남성일 경우 내부의 변수를 false로 채워주세요", nullable = true)
    private ReproductiveHealthDTO reproductiveHealthDTO;

    @Schema(description = "2번째 질문: 알레르기 관련 질문 DTO", nullable = false)
    private AllergyStatusDTO allergyStatusDTO;

    @Schema(description = "3번째 질문: 건강 상태 질문 DTO", nullable = false)
    private HealthConditionDTO healthConditionDTO;

    @Schema(description = "4번째 질문: 복용 약물 관련 질문 DTO", nullable = false)
    private MedicationUsageDTO medicationUsageDTO;

    @Schema(description = "5번째 질문: 일상 불편함 여부 질문 DTO", nullable = false)
    private DailyDiscomfortDTO dailyDiscomfortDTO;

}
