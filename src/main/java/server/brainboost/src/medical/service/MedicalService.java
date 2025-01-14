package server.brainboost.src.medical.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.CognitiveDomain;
import server.brainboost.config.Status;
import server.brainboost.enums.AllergyTag;
import server.brainboost.enums.ConditionTag;
import server.brainboost.enums.DiscomfortTag;
import server.brainboost.enums.MedicineTag;
import server.brainboost.enums.PregnancyTag;
import server.brainboost.src.medical.dto.AllergyStatusDTO;
import server.brainboost.src.medical.dto.DailyDiscomfortDTO;
import server.brainboost.src.medical.dto.HealthConditionDTO;
import server.brainboost.src.medical.dto.MedicalChecklistDTO;
import server.brainboost.src.medical.dto.MedicationUsageDTO;
import server.brainboost.src.medical.dto.NutrientMainDomainDTO;
import server.brainboost.src.medical.dto.NutrientSubDomainDTO;
import server.brainboost.src.medical.dto.NutrientSuggestionDto;
import server.brainboost.src.medical.dto.ReproductiveHealthDTO;
import server.brainboost.src.medical.entity.MedicalChecklistEntity;
import server.brainboost.src.medical.entity.UserAllergyEntity;
import server.brainboost.src.medical.entity.UserConditionEntity;
import server.brainboost.src.medical.entity.UserDiscomfortEntity;
import server.brainboost.src.medical.entity.UserMedicineEntity;
import server.brainboost.src.medical.entity.UserPregnancyEntity;
import server.brainboost.src.medical.repository.MedicalChecklistRepository;
import server.brainboost.src.medical.repository.MedicalRepository;
import server.brainboost.src.medical.repository.UserAllergyRepository;
import server.brainboost.src.medical.repository.UserConditionRepository;
import server.brainboost.src.medical.repository.UserDiscomfortRepository;
import server.brainboost.src.medical.repository.UserMedicineRepository;
import server.brainboost.src.medical.repository.UserPregnancyRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MedicalService {

    private final UserRepository userRepository;
    private final MedicalChecklistRepository medicalChecklistRepository;
    private final UserAllergyRepository userAllergyRepository;
    private final UserConditionRepository userConditionRepository;
    private final UserDiscomfortRepository userDiscomfortRepository;
    private final UserMedicineRepository userMedicineRepository;
    private final UserPregnancyRepository userPregnancyRepository;
    private final MedicalRepository medicalRepository;


    @Transactional
    public void createMedicalCheckList(Long userId, MedicalChecklistDTO medicalChecklistDTO) throws BaseException{

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        if(!(user.getGender().equals('M') || user.getGender().equals('W'))){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
        }

        MedicalChecklistEntity medicalChecklist = user.getMedicalChecklist();

        // 기존에 건강 체크 리스트를 작성하지 않은 경우
        if(medicalChecklist == null){
            medicalChecklist = new MedicalChecklistEntity(medicalChecklistDTO, user);
            user.setMedicalChecklist(medicalChecklist);
            medicalChecklistRepository.save(medicalChecklist);
        }
        else{ // 기존에 건강 체크 리스트가 작성된 경우
           throw new BaseException(BaseResponseStatus.MEDICAL_CHECKLIST_ALREADY_EXIST);
        }

        List<UserPregnancyEntity> userPregnancyEntityList;
        List<UserConditionEntity> userConditionEntityList;
        List<UserAllergyEntity> userAllergyEntityList;
        List<UserMedicineEntity> userMedicineEntityList;
        List<UserDiscomfortEntity> userDiscomfortEntityList;

        // 각 user_medical info 에 정보 넣기
        if(user.getGender().equals('M')){
            userConditionEntityList = createUserConditionList(medicalChecklistDTO.getHealthConditionDTO(), user);
            userAllergyEntityList = createUserAllergyList(medicalChecklistDTO.getAllergyStatusDTO(), user);
            userMedicineEntityList = createUserMedicineList(medicalChecklistDTO.getMedicationUsageDTO(), user);
            userDiscomfortEntityList = createUserDiscomfortList(medicalChecklistDTO.getDailyDiscomfortDTO(), user);

        }
        else{
            userPregnancyEntityList = createUserPregnancyList(medicalChecklistDTO.getReproductiveHealthDTO(), user);
            userConditionEntityList = createUserConditionList(medicalChecklistDTO.getHealthConditionDTO(), user);
            userAllergyEntityList = createUserAllergyList(medicalChecklistDTO.getAllergyStatusDTO(), user);
            userMedicineEntityList = createUserMedicineList(medicalChecklistDTO.getMedicationUsageDTO(), user);
            userDiscomfortEntityList = createUserDiscomfortList(medicalChecklistDTO.getDailyDiscomfortDTO(), user);

            userPregnancyRepository.saveAll(userPregnancyEntityList);
        }

        userConditionRepository.saveAll(userConditionEntityList);
        userAllergyRepository.saveAll(userAllergyEntityList);
        userMedicineRepository.saveAll(userMedicineEntityList);
        userDiscomfortRepository.saveAll(userDiscomfortEntityList);


    }

    @Transactional
    public void updateMedicalCheckList(Long userId, MedicalChecklistDTO medicalCheckListDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
            .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        if(!(user.getGender().equals('M') || user.getGender().equals('W'))){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
        }

        MedicalChecklistEntity medicalChecklist = user.getMedicalChecklist();

        // 기존에 건강 체크 리스트를 작성하지 않은 경우
        if(medicalChecklist == null){
            throw new BaseException(BaseResponseStatus.MEDICAL_CHECKLIST_NO_EXIST);
        }
        else{ // 기존에 건강 체크 리스트가 작성된 경우

            medicalChecklist.updateMedicalChecklist(medicalCheckListDTO);
            medicalChecklistRepository.save(medicalChecklist);
        }

        // 각 user_info 정보 삭제
        medicalChecklistRepository.deleteUserAllergy(user);
        medicalChecklistRepository.deleteUserCondition(user);
        medicalChecklistRepository.deleteUserDiscomfort(user);
        medicalChecklistRepository.deleteUserMedicine(user);
        medicalChecklistRepository.deleteUserPregnancy(user);

        List<UserPregnancyEntity> userPregnancyEntityList;
        List<UserConditionEntity> userConditionEntityList;
        List<UserAllergyEntity> userAllergyEntityList;
        List<UserMedicineEntity> userMedicineEntityList;
        List<UserDiscomfortEntity> userDiscomfortEntityList;

        // 각 user_medical info 에 정보 넣기
        if(user.getGender().equals('M')){
            userConditionEntityList = createUserConditionList(medicalCheckListDTO.getHealthConditionDTO(), user);
            userAllergyEntityList = createUserAllergyList(medicalCheckListDTO.getAllergyStatusDTO(), user);
            userMedicineEntityList = createUserMedicineList(medicalCheckListDTO.getMedicationUsageDTO(), user);
            userDiscomfortEntityList = createUserDiscomfortList(medicalCheckListDTO.getDailyDiscomfortDTO(), user);

        }
        else{
            userPregnancyEntityList = createUserPregnancyList(medicalCheckListDTO.getReproductiveHealthDTO(), user);
            userConditionEntityList = createUserConditionList(medicalCheckListDTO.getHealthConditionDTO(), user);
            userAllergyEntityList = createUserAllergyList(medicalCheckListDTO.getAllergyStatusDTO(), user);
            userMedicineEntityList = createUserMedicineList(medicalCheckListDTO.getMedicationUsageDTO(), user);
            userDiscomfortEntityList = createUserDiscomfortList(medicalCheckListDTO.getDailyDiscomfortDTO(), user);

            userPregnancyRepository.saveAll(userPregnancyEntityList);
        }

        userConditionRepository.saveAll(userConditionEntityList);
        userAllergyRepository.saveAll(userAllergyEntityList);
        userMedicineRepository.saveAll(userMedicineEntityList);
        userDiscomfortRepository.saveAll(userDiscomfortEntityList);


    }




    @Transactional
    public NutrientSuggestionDto recommendNutrients(Long userId, CognitiveDomain typeName) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
            .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        NutrientMainDomainDTO nutrientMainDomainDTO = new NutrientMainDomainDTO();
        NutrientSubDomainDTO nutrientSubDomainDTO = new NutrientSubDomainDTO();

        try{
            if(user.getGender().equals('W')){
                nutrientMainDomainDTO.setNutrientDTOList(medicalRepository.recommendMainNutrientsForWoman(userId, typeName));
                nutrientSubDomainDTO.setNutrientDTOList(medicalRepository.recommendSubNutrientsForWoman(userId, typeName));
            }
            else{
                nutrientMainDomainDTO.setNutrientDTOList(medicalRepository.recommendMainNutrientsForMan(userId, typeName));
                nutrientSubDomainDTO.setNutrientDTOList(medicalRepository.recommendSubNutrientsForMan(userId, typeName));
            }
            return new NutrientSuggestionDto(nutrientMainDomainDTO, nutrientSubDomainDTO);
        }catch (Exception e){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }
    }















    private List<UserPregnancyEntity> createUserPregnancyList(ReproductiveHealthDTO reproductiveHealthDTO, UserEntity user){
        List<UserPregnancyEntity> userPregnancyEntityList = new ArrayList<>();

        if (Boolean.TRUE.equals(reproductiveHealthDTO.getIsPregnant())) {
            userPregnancyEntityList.add(new UserPregnancyEntity(PregnancyTag.PREGNANT, user));
        }
        if (Boolean.TRUE.equals(reproductiveHealthDTO.getIsBreastfeeding())) {
            userPregnancyEntityList.add(new UserPregnancyEntity(PregnancyTag.BREASTFEEDING, user));
        }
        if (Boolean.TRUE.equals(reproductiveHealthDTO.getIsPlanningChild())) {
            userPregnancyEntityList.add(new UserPregnancyEntity(PregnancyTag.PLANNING_CHILD, user));
        }
        if (Boolean.TRUE.equals(reproductiveHealthDTO.getIsMenopause())) {
            userPregnancyEntityList.add(new UserPregnancyEntity(PregnancyTag.MENOPAUSE, user));
        }

        return userPregnancyEntityList;
    }


    private List<UserAllergyEntity> createUserAllergyList(AllergyStatusDTO allergyStatusDTO, UserEntity user){
        List<UserAllergyEntity> userAllergyEntityList = new ArrayList<>();

        if (Boolean.TRUE.equals(allergyStatusDTO.getIsUnknownAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.UNKNOWN, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsPeanutAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.PEANUT, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsShellfishAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.SHELLFISH, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsEstrogenAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.ESTROGEN, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsLacquerAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.LACQUER, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsBarleyAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.BARLEY, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsCaffeineAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.CAFFEINE, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsSpecificAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.SPECIFIC, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsSoyAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.SOY, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsPollenAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.POLLEN, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsDairyAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.DAIRY, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsFigAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.FIG, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsHopExtractAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.HOP_EXTRACT, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsNutAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.NUT, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsMountainAshAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.MOUNTAIN_ASH, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsEveningPrimroseAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.EVENING_PRIMROSE, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsWheatAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.WHEAT, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsEggAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.EGG, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsPropolisAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.PROPOLIS, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsGinsengAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.GINSENG, user));
        }
        if (Boolean.TRUE.equals(allergyStatusDTO.getIsCitrusAllergy())) {
            userAllergyEntityList.add(new UserAllergyEntity(AllergyTag.CITRUS, user));
        }

        return userAllergyEntityList;
    }

    private List<UserConditionEntity> createUserConditionList(HealthConditionDTO healthConditionDTO, UserEntity user){

        List<UserConditionEntity> userConditionEntityList = new ArrayList<>();

        if (Boolean.TRUE.equals(healthConditionDTO.getIsHyperlipidemiaCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.HYPERLIPIDEMIA, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsHypertensionCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.HYPERTENSION, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsLiverCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.LIVER, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsOsteoporosisCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.OSTEOPOROSIS, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsDiabetesCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.DIABETES, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsJointCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.JOINT, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsAllergyCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.ALLERGY, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsKidneyCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.KIDNEY, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsHypotensionCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.HYPOTENSION, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsVascularCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.VASCULAR, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsGallbladderCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.GALLBLADDER, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsCancerCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.CANCER, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsAsthmaCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.ASTHMA, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsCirculatoryCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.CIRCULATORY, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsStomachCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.STOMACH, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsSkinCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.SKIN, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsPostSurgeryCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.POST_SURGERY, user));
        }
        if (Boolean.TRUE.equals(healthConditionDTO.getIsHeartCondition())) {
            userConditionEntityList.add(new UserConditionEntity(ConditionTag.HEART, user));
        }

        return userConditionEntityList;
    }

    private List<UserMedicineEntity> createUserMedicineList(MedicationUsageDTO medicationUsageDTO, UserEntity user){

        List<UserMedicineEntity> userMedicineEntityList = new ArrayList<>();

        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntihyperlipidemic())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIHYPERLIPIDEMIC, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntihypertensive())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIHYPERTENSIVE, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingHormonal())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.HORMONAL, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingImmunosuppressant())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.IMMUNOSUPPRESSANT, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntiarrhythmic())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIARRHYTHMIC, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingNeuroleptic())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.NEUROLEPTIC, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntacid())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTACID, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingNSAID())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.NSAID, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntithromboticAgent())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTITHROMBOTIC_AGENT, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingCNSDepressant())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.CNS_DEPRESSANT, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntidepressant())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIDEPRESSANT, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAnticoagulant())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTICOAGULANT, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntiplatelet())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIPLATELET, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingAntidiabetic())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.ANTIDIABETIC, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingSedative())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.SEDATIVE, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingBloodThinner())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.BLOOD_THINNER, user));
        }
        if (Boolean.TRUE.equals(medicationUsageDTO.getIsTakingNephrotoxicDrug())) {
            userMedicineEntityList.add(new UserMedicineEntity(MedicineTag.NEPHROTOXIC_DRUG, user));
        }

        return userMedicineEntityList;
    }

    private List<UserDiscomfortEntity> createUserDiscomfortList(DailyDiscomfortDTO dailyDiscomfortDTO, UserEntity user){
        List<UserDiscomfortEntity> userDiscomfortEntityList = new ArrayList<>();

        if (Boolean.TRUE.equals(dailyDiscomfortDTO.getIsExperiencingSleepDifficulty())){
            userDiscomfortEntityList.add(new UserDiscomfortEntity(DiscomfortTag.SLEEP_DIFFICULTY, user));
        }
        if (Boolean.TRUE.equals(dailyDiscomfortDTO.getIsExperiencingStress())){
            userDiscomfortEntityList.add(new UserDiscomfortEntity(DiscomfortTag.STRESS, user));
        }
        if (Boolean.TRUE.equals(dailyDiscomfortDTO.getIsExperiencingMemoryLoss())){
            userDiscomfortEntityList.add(new UserDiscomfortEntity(DiscomfortTag.MEMORY_LOSS, user));
        }
        if (Boolean.TRUE.equals(dailyDiscomfortDTO.getIsExperiencingSensitivity())){
            userDiscomfortEntityList.add(new UserDiscomfortEntity(DiscomfortTag.SENSITIVITY, user));
        }
        if (Boolean.TRUE.equals(dailyDiscomfortDTO.getIsExperiencingDepression())){
            userDiscomfortEntityList.add(new UserDiscomfortEntity(DiscomfortTag.DEPRESSION, user));
        }

        return userDiscomfortEntityList;
    }

}
