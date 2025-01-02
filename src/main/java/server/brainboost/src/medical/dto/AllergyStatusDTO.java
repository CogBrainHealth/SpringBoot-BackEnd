package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Schema(description = "2번째 질문: 알레르기 관련 질문 DTO")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AllergyStatusDTO {

    // 2번째 질문: 알레르기 여부
    @Schema(description = "두 번째 질문: 알레르기-원인 알 수 없음", nullable = false, example = "false")
    private Boolean isUnknownAllergy;        // 원인 알 수 없음

    @Schema(description = "두 번째 질문: 알레르기-땅콩", nullable = false, example = "false")
    private Boolean isPeanutAllergy;         // 땅콩

    @Schema(description = "두 번째 질문: 알레르기-갑각류", nullable = false, example = "true")
    private Boolean isShellfishAllergy;      // 갑각류

    @Schema(description = "두 번째 질문: 알레르기-에스트로겐 민감", nullable = false, example = "false")
    private Boolean isEstrogenAllergy;       // 에스트로겐 민감

    @Schema(description = "두 번째 질문: 알레르기-옻", nullable = false, example = "false")
    private Boolean isLacquerAllergy;       // 옻

    @Schema(description = "두 번째 질문: 알레르기-소맥/보리", nullable = false, example = "false")
    private Boolean isBarleyAllergy;        // 소맥/보리

    @Schema(description = "두 번째 질문: 알레르기-카페인 민감", nullable = false, example = "false")
    private Boolean isCaffeineAllergy;       // 카페인 민감

    @Schema(description = "두 번째 질문: 알레르기-특정 알레르기", nullable = false, example = "true")
    private Boolean isSpecificAllergy;       // 특정 알레르기

    @Schema(description = "두 번째 질문: 알레르기-대두", nullable = false, example = "false")
    private Boolean isSoyAllergy;            // 대두

    @Schema(description = "두 번째 질문: 알레르기-호박씨", nullable = false, example = "false")
    private Boolean isPollenAllergy;         // 호박씨

    @Schema(description = "두 번째 질문: 알레르기-유제품/유당불내증", nullable = false, example = "true")
    private Boolean isDairyAllergy;          // 유제품/유당불내증

    @Schema(description = "두 번째 질문: 알레르기-무화과", nullable = false, example = "false")
    private Boolean isFigAllergy;            // 무화과

    @Schema(description = "두 번째 질문: 알레르기-호프추출물", nullable = false, example = "false")
    private Boolean isHopExtractAllergy;     // 호프추출물

    @Schema(description = "두 번째 질문: 알레르기-사상자", nullable = false, example = "false")
    private Boolean isNutAllergy;            // 사상자

    @Schema(description = "두 번째 질문: 알레르기-산수유", nullable = false, example = "false")
    private Boolean isMountainAshAllergy;    // 산수유

    @Schema(description = "두 번째 질문: 알레르기-달맞이꽃종자유", nullable = false, example = "false")
    private Boolean isEveningPrimroseAllergy; // 달맞이꽃종자유

    @Schema(description = "두 번째 질문: 알레르기-밀", nullable = false, example = "true")
    private Boolean isWheatAllergy;          // 밀

    @Schema(description = "두 번째 질문: 알레르기-난황/계란", nullable = false, example = "true")
    private Boolean isEggAllergy;            // 난황/계란

    @Schema(description = "두 번째 질문: 알레르기-프로폴리스", nullable = false, example = "false")
    private Boolean isPropolisAllergy;       // 프로폴리스

    @Schema(description = "두 번째 질문: 알레르기-홍삼", nullable = false, example = "false")
    private Boolean isGinsengAllergy;        // 홍삼

    @Schema(description = "두 번째 질문: 알레르기-국화과/쑥갓/카모마일/해바라기씨", nullable = false, example = "false")
    private Boolean isCitrusAllergy;         // 국화과/쑥갓/카모마일/해바라기씨


}
