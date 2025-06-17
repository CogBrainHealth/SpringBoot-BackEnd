package server.brainboost.src.medical.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalTime;

public class MedicalRequestDTO {

    @Setter
    @Getter
    @Schema(description = "개인 정보 건강 리스트 정보를 가져올 DTO")
    public static class MedicalChecklistDTO {

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

    @Getter
    @Setter
    @Schema(description = "2번째 질문: 알레르기 관련 질문 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AllergyStatusDTO {

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

    @Getter
    @Setter
    @Schema(description = "5번째 질문: 일상 불편함 여부 질문 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DailyDiscomfortDTO {

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

    @Getter
    @Setter
    @Schema(description = "3번째 질문: 건강 상태 질문 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class HealthConditionDTO {

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

    @Getter
    @Setter
    @Schema(description = "1번째 질문: 출산/임신 관련 상태 질문 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ReproductiveHealthDTO {

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

    @Getter
    @Setter
    @Schema(description = "4번째 질문: 복용 약물 관련 질문 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MedicationUsageDTO {

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

    @Getter
    @Setter
    @Schema(description = "프리미엄 개인 정보 건강 리스트 정보를 담을 DTO")
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PremiumMedicalChecklistDTO {

        @Schema(description = "1번 문항", nullable = false, example = "1")
        private Integer number1;

        @Schema(description = "2번 문항", nullable = false, example = "3")
        private Integer number2;

        @Schema(description = "3번 문항", nullable = false, example = "1")
        private Integer number3;

        @Schema(description = "4번 문항", nullable = false, example = "1")
        private Integer number4;

        @Schema(description = "5번 문항", nullable = false, example = "1")
        private Integer number5;

        @Schema(description = "6번 문항", nullable = false, example = "1")
        private Integer number6;

        @Schema(description = "7번 문항", nullable = false, example = "1")
        private Integer number7;

        @Schema(description = "8번 문항", nullable = false, example = "1")
        private Integer number8;

        @Schema(description = "9번 문항", nullable = false, example = "1")
        private Integer number9;

        @Schema(description = "10번 문항", nullable = false, example = "1")
        private Integer number10;

        @Schema(description = "11번 문항", nullable = false, example = "1")
        private Integer number11;

        @Schema(description = "12번 문항", nullable = false, example = "1")
        private Integer number12;

        @Schema(description = "13번 문항", nullable = false, example = "1")
        private Integer number13;

        @Schema(description = "걸린 시간", nullable = true, example = "14:30:15")
        private LocalTime localTime;

        public Integer getTotalScore(){
            return number1 + number2 + number3+ number4 + number5 + number6 + number7 + number8 + number9
                + number10 + number11 + number12 + number13;
        }

    }
}
