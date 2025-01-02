package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "3번째 질문: 건강 상태 질문 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HealthConditionDTO {

    // 3번째 질문: 기저질환 여부
    @Schema(description = "세 번째 질문: 기저질환 여부-고칼슘혈증", nullable = false, example = "false")
    private Boolean isHyperlipidemiaCondition;    // 고칼슘혈증

    @Schema(description = "세 번째 질문: 기저질환 여부-고혈압", nullable = false, example = "true")
    private Boolean isHypertensionCondition;      // 고혈압

    @Schema(description = "세 번째 질문: 기저질환 여부-갑상선", nullable = false, example = "false")
    private Boolean isLiverCondition;             // 간상선

    @Schema(description = "세 번째 질문: 기저질환 여부-골다공증", nullable = false, example = "false")
    private Boolean isOsteoporosisCondition;      // 골다공증

    @Schema(description = "세 번째 질문: 기저질환 여부-당뇨", nullable = false, example = "true")
    private Boolean isDiabetesCondition;          // 당뇨

    @Schema(description = "세 번째 질문: 기저질환 여부-뼈/관절", nullable = false, example = "false")
    private Boolean isJointCondition;             // 뼈/관절

    @Schema(description = "세 번째 질문: 기저질환 여부-알레르기", nullable = false, example = "false")
    private Boolean isAllergyCondition;           // 알레르기

    @Schema(description = "세 번째 질문: 기저질환 여부-신장", nullable = false, example = "true")
    private Boolean isKidneyCondition;            // 신장

    @Schema(description = "세 번째 질문: 기저질환 여부-저혈압", nullable = false, example = "false")
    private Boolean isHypotensionCondition;       // 저혈압

    @Schema(description = "세 번째 질문: 기저질환 여부-혈관이상지질혈증", nullable = false, example = "false")
    private Boolean isVascularCondition;          // 혈관/이상지질혈증

    @Schema(description = "세 번째 질문: 기저질환 여부-질환/비염/결막염", nullable = false, example = "false")
    private Boolean isGallbladderCondition;       // 질환/비염/결막염

    @Schema(description = "세 번째 질문: 기저질환 여부-각종 암", nullable = false, example = "false")
    private Boolean isCancerCondition;            // 각종 암

    @Schema(description = "세 번째 질문: 기저질환 여부-천식", nullable = false, example = "false")
    private Boolean isAsthmaCondition;            // 천식

    @Schema(description = "세 번째 질문: 기저질환 여부-혈액응고관련 질환", nullable = false, example = "false")
    private Boolean isCirculatoryCondition;       // 혈액응고관련 질환

    @Schema(description = "세 번째 질문: 기저질환 여부-위장", nullable = false, example = "false")
    private Boolean isStomachCondition;           // 위장

    @Schema(description = "세 번째 질문: 기저질환 여부-피부 광과민성", nullable = false, example = "false")
    private Boolean isSkinCondition;              // 피부 광과민성

    @Schema(description = "세 번째 질문: 기저질환 여부-수술 전후", nullable = false, example = "false")
    private Boolean isPostSurgeryCondition;       // 수술 전후

    @Schema(description = "세 번째 질문: 기저질환 여부-심장질환/심근경색/스텐트 시술", nullable = false, example = "false")
    private Boolean isHeartCondition;             // 심장질환/심근경색/스텐트 시술



}
