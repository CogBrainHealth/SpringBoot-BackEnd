package server.brainboost.src.medical.dto.converter;

import server.brainboost.code.status.ErrorStatus;
import server.brainboost.src.medical.entity.checklist.enums.PremiumScoreLevel;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.medical.dto.MedicalResponseDTO;
import server.brainboost.src.medical.entity.checklist.MedicalChecklistEntity;
import server.brainboost.src.medical.entity.checklist.PremiumMedicalChecklistEntity;
import server.brainboost.src.medical.entity.food.MealPlanEntity;
import server.brainboost.src.medical.entity.nutrient.NutrientEntity;
import server.brainboost.src.medical.entity.nutrientCombinations.NutrientCombinationsEntity;

import java.util.List;
import java.util.stream.Collectors;

public class MedicalConverter {

    public static MedicalResponseDTO.NutrientResponseDTO toNutrientResponseDTO(NutrientEntity nutrientEntity) {
        return MedicalResponseDTO.NutrientResponseDTO.builder()
                .nutrientId(nutrientEntity.getId())
                .nutrientName(nutrientEntity.getNutrientName().name())
                .details(nutrientEntity.getDetails())
                .build();
    }

    public static MedicalResponseDTO.MedicalChecklistResponseDTO toMedicalChecklistResponseDTO(MedicalChecklistEntity medicalChecklist) {

        MedicalResponseDTO.MedicalChecklistResponseDTO medicalChecklistResponseDTO = new MedicalResponseDTO.MedicalChecklistResponseDTO();
        MedicalResponseDTO.ReproductiveHealthDTO reproductiveHealthDTO = null;
        MedicalResponseDTO.HealthConditionDTO healthConditionDTO = null;
        MedicalResponseDTO.AllergyStatusDTO allergyStatusDTO = null;
        MedicalResponseDTO.MedicationUsageDTO medicationUsageDTO = null;
        MedicalResponseDTO.DailyDiscomfortDTO dailyDiscomfortDTO = null;

        // 기존에 건강 체크 리스트를 작성하지 않은 경우
        if(medicalChecklist == null){
            throw new GeneralException(ErrorStatus.MEDICAL_CHECKLIST_NO_EXIST);
        }

        // 여자만 있는 항목
        reproductiveHealthDTO = new MedicalResponseDTO.ReproductiveHealthDTO(
                medicalChecklist.getIsPregnant(),
                medicalChecklist.getIsBreastfeeding(),
                medicalChecklist.getIsPlanningChild(),
                medicalChecklist.getIsMenopause()
        );

        //남녀 모두 있는 항목
        healthConditionDTO = new MedicalResponseDTO.HealthConditionDTO(
                medicalChecklist.getIsHyperlipidemiaCondition(),
                medicalChecklist.getIsHypertensionCondition(),
                medicalChecklist.getIsLiverCondition(),
                medicalChecklist.getIsOsteoporosisCondition(),
                medicalChecklist.getIsDiabetesCondition(),
                medicalChecklist.getIsJointCondition(),
                medicalChecklist.getIsAllergyCondition(),
                medicalChecklist.getIsKidneyCondition(),
                medicalChecklist.getIsHypotensionCondition(),
                medicalChecklist.getIsVascularCondition(),
                medicalChecklist.getIsGallbladderCondition(),
                medicalChecklist.getIsCancerCondition(),
                medicalChecklist.getIsAsthmaCondition(),
                medicalChecklist.getIsCirculatoryCondition(),
                medicalChecklist.getIsStomachCondition(),
                medicalChecklist.getIsSkinCondition(),
                medicalChecklist.getIsPostSurgeryCondition(),
                medicalChecklist.getIsHeartCondition()
        );

        //남녀 모두 있는 항목
        allergyStatusDTO = new MedicalResponseDTO.AllergyStatusDTO(
                medicalChecklist.getIsUnknownAllergy(),
                medicalChecklist.getIsPeanutAllergy(),
                medicalChecklist.getIsShellfishAllergy(),
                medicalChecklist.getIsEstrogenAllergy(),
                medicalChecklist.getIsLacquerAllergy(),
                medicalChecklist.getIsBarleyAllergy(),
                medicalChecklist.getIsCaffeineAllergy(),
                medicalChecklist.getIsSpecificAllergy(),
                medicalChecklist.getIsSoyAllergy(),
                medicalChecklist.getIsPollenAllergy(),
                medicalChecklist.getIsDairyAllergy(),
                medicalChecklist.getIsFigAllergy(),
                medicalChecklist.getIsHopExtractAllergy(),
                medicalChecklist.getIsNutAllergy(),
                medicalChecklist.getIsMountainAshAllergy(),
                medicalChecklist.getIsEveningPrimroseAllergy(),
                medicalChecklist.getIsWheatAllergy(),
                medicalChecklist.getIsEggAllergy(),
                medicalChecklist.getIsPropolisAllergy(),
                medicalChecklist.getIsGinsengAllergy(),
                medicalChecklist.getIsCitrusAllergy()
        );

        //남녀 모두 있는 항목
        medicationUsageDTO = new MedicalResponseDTO.MedicationUsageDTO(
                medicalChecklist.getIsTakingAntihyperlipidemic(),
                medicalChecklist.getIsTakingAntihypertensive(),
                medicalChecklist.getIsTakingHormonal(),
                medicalChecklist.getIsTakingImmunosuppressant(),
                medicalChecklist.getIsTakingAntiarrhythmic(),
                medicalChecklist.getIsTakingNeuroleptic(),
                medicalChecklist.getIsTakingAntacid(),
                medicalChecklist.getIsTakingNSAID(),
                medicalChecklist.getIsTakingAntithromboticAgent(),
                medicalChecklist.getIsTakingCNSDepressant(),
                medicalChecklist.getIsTakingAntidepressant(),
                medicalChecklist.getIsTakingAnticoagulant(),
                medicalChecklist.getIsTakingAntiplatelet(),
                medicalChecklist.getIsTakingAntidiabetic(),
                medicalChecklist.getIsTakingSedative(),
                medicalChecklist.getIsTakingBloodThinner(),
                medicalChecklist.getIsTakingNephrotoxicDrug()
        );

        dailyDiscomfortDTO = new MedicalResponseDTO.DailyDiscomfortDTO(
                medicalChecklist.getIsExperiencingSleepDifficulty(),
                medicalChecklist.getIsExperiencingStress(),
                medicalChecklist.getIsExperiencingMemoryLoss(),
                medicalChecklist.getIsExperiencingSensitivity(),
                medicalChecklist.getIsExperiencingDepression()
        );

        medicalChecklistResponseDTO.setReproductiveHealthDTO(reproductiveHealthDTO);
        medicalChecklistResponseDTO.setHealthConditionDTO(healthConditionDTO);
        medicalChecklistResponseDTO.setAllergyStatusDTO(allergyStatusDTO);
        medicalChecklistResponseDTO.setMedicationUsageDTO(medicationUsageDTO);
        medicalChecklistResponseDTO.setDailyDiscomfortDTO(dailyDiscomfortDTO);

        return medicalChecklistResponseDTO;


    }

    public static MedicalResponseDTO.PremiumMedicalChecklistResponseDTO toPremiumMedicalChecklistResponseDTO(PremiumMedicalChecklistEntity premiumMedicalChecklistEntity, List<NutrientCombinationsEntity> nutrientCombinationsEntityList, List<MealPlanEntity> mealPlanEntityList){

        PremiumScoreLevel level = PremiumScoreLevel.of(premiumMedicalChecklistEntity.getTotalScore());

        //TODO DB 마이그레이션 공부 및 적용해보기

        return MedicalResponseDTO.PremiumMedicalChecklistResponseDTO.builder()
                .evaluation(level.getEvaluation())
                .totalScore(premiumMedicalChecklistEntity.getTotalScore())
                .attentionScore(premiumMedicalChecklistEntity.getAttentionScore())
                .memoryScore(premiumMedicalChecklistEntity.getMemoryScore())
                .spatialPerceptionScore(premiumMedicalChecklistEntity.getSpatialPerceptionScore())
                .comment(level.getComment())
                .feedbackList(level.getFeedbackList())
                .nutrientCombinationsDTOList(toListNutrientCombinationsDTO(nutrientCombinationsEntityList))
                .mealPlanDTOList(toListMealPlanDTO(mealPlanEntityList))
                .build();
    }

    public static List<MedicalResponseDTO.MealPlanDTO> toListMealPlanDTO(List<MealPlanEntity> mealPlanEntityList){
        return mealPlanEntityList.stream()
                .map(entity -> MedicalResponseDTO.MealPlanDTO.builder()
                        .foodName(entity.getFoodName())
                        .mealPeriod(entity.getMealPeriod())
                        .build())
                .collect(Collectors.toList());

    }

    public static List<MedicalResponseDTO.NutrientCombinationsDTO> toListNutrientCombinationsDTO(List<NutrientCombinationsEntity> nutrientCombinationsEntityList){
        return nutrientCombinationsEntityList.stream()
                .map(entity -> MedicalResponseDTO.NutrientCombinationsDTO.builder()
                        .nutrientId(entity.getNutrientEntity().getId())
                        .nutrientName(entity.getNutrientEntity().getNutrientName().name())
                        .build())
                .collect(Collectors.toList());
    }

}
