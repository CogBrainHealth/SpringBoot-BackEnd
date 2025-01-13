package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "4번째 질문: 복용 약물 관련 질문 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MedicationUsageDTO {

    //4번째 질문: 복용 중인 약 여부
    @Schema(description = "네 번째 질문: 복용 중인 약 여부-고지혈증약", nullable = false, example = "false")
    private Boolean isTakingAntihyperlipidemic;     // 고지혈증약

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-고혈압약", nullable = false, example = "false")
    private Boolean isTakingAntihypertensive;      // 고혈압약

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-호르몬제", nullable = false, example = "false")
    private Boolean isTakingHormonal;              // 호르몬제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-먼역억제제", nullable = false, example = "false")
    private Boolean isTakingImmunosuppressant;     // 면역억제제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-부정맥 치료제", nullable = false, example = "false")
    private Boolean isTakingAntiarrhythmic;         // 부정맥 치료제 (ANTIARRHYTHMIC)

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-신경안정제", nullable = false, example = "true")
    private Boolean isTakingNeuroleptic;           // 신경안정제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-위산분비억제제", nullable = false, example = "false")
    private Boolean isTakingAntacid;               // 위산분비억제제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-비스테로이드성 항염증제", nullable = false, example = "false")
    private Boolean isTakingNSAID;               // 비스테로이드성 항염증제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-항혈전제", nullable = false, example = "false")
    private Boolean isTakingAntithromboticAgent;         // 항혈전제 (항혈전제에 해당, Enum: Antiplatelet)

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-중추신경억제제", nullable = false, example = "false")
    private Boolean isTakingCNSDepressant;         // 중추신경억제제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-항우울증약", nullable = false, example = "false")
    private Boolean isTakingAntidepressant;        // 항우울증약

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-항응고약", nullable = false, example = "false")
    private Boolean isTakingAnticoagulant;     // 항응고약

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-항혈소판제", nullable = false, example = "false")
    private Boolean isTakingAntiplatelet;          // 항혈소판제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-당뇨약", nullable = false, example = "true")
    private Boolean isTakingAntidiabetic;          // 당뇨약

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-수면유도제", nullable = false, example = "false")
    private Boolean isTakingSedative;              // 수면유도제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-혈전용해제", nullable = false, example = "false")
    private Boolean isTakingBloodThinner;          // 혈전용해제

    @Schema(description = "네 번째 질문: 복용 중인 약 여부-신장에 영향을 미치는 약품", nullable = false, example = "false")
    private Boolean isTakingNephrotoxicDrug;       // 신장에 영향을 미치는 약품

}
