package server.brainboost.src.medical.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import server.brainboost.base.BaseEntity;
import server.brainboost.src.user.entity.UserEntity;


@Entity
@Getter
@Table(name = "medical_checklist")
@NoArgsConstructor
@DynamicInsert
public class MedicalChecklist extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 1번째 질문: 해당 사항
    private Boolean isPregnant;    // 임신 중
    private Boolean isBreastfeeding; // 수유 중
    private Boolean isPlanningChild; // 자녀 계획 여부
    private Boolean isMenopause;     // 폐경 여부

    // 2번째 질문: 알레르기 여부
    private Boolean isUnknownAllergy;        // 원인 알 수 없음
    private Boolean isPeanutAllergy;         // 땅콩
    private Boolean isShellfishAllergy;      // 갑각류
    private Boolean isEstrogenAllergy;       // 에스트로겐 민감
    private Boolean isClothingAllergy;       // 옷
    private Boolean isPerfumeAllergy;        // 소맥/보리
    private Boolean isCaffeineAllergy;       // 카페인 민감
    private Boolean isSpecificAllergy;       // 특정 알레르기
    private Boolean isSoyAllergy;            // 대두
    private Boolean isPollenAllergy;         // 호박씨
    private Boolean isDairyAllergy;          // 유제품/유당불내증
    private Boolean isFigAllergy;            // 무화과
    private Boolean isHopExtractAllergy;     // 호프추출물
    private Boolean isNutAllergy;            // 사상자
    private Boolean isMountainAshAllergy;    // 산수유
    private Boolean isEveningPrimroseAllergy; // 달맞이꽃종자유
    private Boolean isWheatAllergy;          // 밀
    private Boolean isEggAllergy;            // 난황/계란
    private Boolean isPropolisAllergy;       // 프로폴리스
    private Boolean isGinsengAllergy;        // 홍삼
    private Boolean isCitrusAllergy;         // 국화과/쑥갓/카모마일/해바라기씨



    // 3번째 질문: 기저질환 여부
    private Boolean isHyperlipidemiaCondition;    // 고칼슘혈증
    private Boolean isHypertensionCondition;      // 고혈압
    private Boolean isLiverCondition;             // 간상선
    private Boolean isOsteoporosisCondition;      // 골다공증
    private Boolean isDiabetesCondition;          // 당뇨
    private Boolean isJointCondition;             // 뼈/관절
    private Boolean isAllergyCondition;           // 알레르기
    private Boolean isKidneyCondition;            // 신장
    private Boolean isHypotensionCondition;       // 저혈압
    private Boolean isVascularCondition;          // 혈관/이상지질혈증
    private Boolean isGallbladderCondition;       // 질환/비염/결막염
    private Boolean isCancerCondition;            // 각종 암
    private Boolean isAsthmaCondition;            // 천식
    private Boolean isCirculatoryCondition;       // 혈액응고관련 질환
    private Boolean isStomachCondition;           // 위장
    private Boolean isSkinCondition;              // 피부 광과민성
    private Boolean isPostSurgeryCondition;       // 수술 전후
    private Boolean isHeartCondition;             // 심장질환/심근경색/스텐트 시술


    //4번째 질문: 복용 중인 약 여부
    private Boolean isTakingAntihyperlipidemic;     // 고지혈증약
    private Boolean isTakingAntihypertensive;      // 고혈압약
    private Boolean isTakingHormonal;              // 호르몬제
    private Boolean isTakingImmunosuppressant;     // 면역억제제
    private Boolean isTakingImmunotherapy;         // 부정맥 치료제
    private Boolean isTakingNeuroleptic;           // 신경안정제
    private Boolean isTakingAntacid;               // 위산분비억제제
    private Boolean isTakingSteroid;               // 비스테로이드성 항염증제
    private Boolean isTakingAnticoagulant;         // 항혈전제
    private Boolean isTakingCNSDepressant;         // 중추신경억제제
    private Boolean isTakingAntidepressant;        // 항우울증약
    private Boolean isTakingAntihyperglycemic;     // 항응고약
    private Boolean isTakingAntiplatelet;          // 항혈소판제
    private Boolean isTakingAntidiabetic;          // 당뇨약
    private Boolean isTakingSedative;              // 수면유도제
    private Boolean isTakingBloodThinner;          // 혈전용해제
    private Boolean isTakingNephrotoxicDrug;       // 신장에 영향을 미치는 약품


    //5번째 질문: 일상에 불편한 점 존재 여부
    private Boolean isExperiencingSleepDifficulty;  // 잠들기 어렵거나 자주 깸
    private Boolean isExperiencingStress;           // 스트레스
    private Boolean isExperiencingMemoryLoss;       // 기억력 감퇴
    private Boolean isExperiencingSensitivity;      // 예민
    private Boolean isExperiencingDepression;       // 우울



    @OneToOne(mappedBy = "medicalChecklist")
    private UserEntity user;

}
