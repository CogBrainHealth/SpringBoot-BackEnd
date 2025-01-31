package server.brainboost.src.medical.service;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.enums.CognitiveDomain;
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
import server.brainboost.src.medical.dto.NutrientDetails;
import server.brainboost.src.medical.dto.NutrientMainDomainDTO;
import server.brainboost.src.medical.dto.NutrientSubDomainDTO;
import server.brainboost.src.medical.dto.NutrientSuggestionDto;
import server.brainboost.src.medical.dto.ReproductiveHealthDTO;
import server.brainboost.src.medical.entity.MedicalChecklistEntity;
import server.brainboost.src.medical.entity.NutrientEntity;
import server.brainboost.src.medical.entity.UserAllergyEntity;
import server.brainboost.src.medical.entity.UserConditionEntity;
import server.brainboost.src.medical.entity.UserDiscomfortEntity;
import server.brainboost.src.medical.entity.UserMedicineEntity;
import server.brainboost.src.medical.entity.UserPregnancyEntity;
import server.brainboost.src.medical.repository.MedicalChecklistRepository;
import server.brainboost.src.medical.repository.MedicalRepository;
import server.brainboost.src.medical.repository.NutrientRepository;
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
    private final NutrientRepository nutrientRepository;


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

    public MedicalChecklistDTO getMedicalCheckList(Long userId) {
        UserEntity user = userRepository.findUserEntityLeftJoinMedicalChecklistByUserId(userId)
            .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        if(!(user.getGender().equals('M') || user.getGender().equals('W'))){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
        }

        MedicalChecklistEntity medicalChecklist = user.getMedicalChecklist();

        MedicalChecklistDTO medicalChecklistDTO = new MedicalChecklistDTO();
        ReproductiveHealthDTO reproductiveHealthDTO = null;
        HealthConditionDTO healthConditionDTO = null;
        AllergyStatusDTO allergyStatusDTO = null;
        MedicationUsageDTO medicationUsageDTO = null;
        DailyDiscomfortDTO dailyDiscomfortDTO = null;

        // 기존에 건강 체크 리스트를 작성하지 않은 경우
        if(medicalChecklist == null){
            throw new BaseException(BaseResponseStatus.MEDICAL_CHECKLIST_NO_EXIST);
        }

        if(user.getGender().equals('W')){
           reproductiveHealthDTO = new ReproductiveHealthDTO(
               medicalChecklist.getIsPregnant(),
               medicalChecklist.getIsBreastfeeding(),
               medicalChecklist.getIsPlanningChild(),
               medicalChecklist.getIsMenopause()
           );
        }

        //남녀 모두 있는 항목
        healthConditionDTO = new HealthConditionDTO(
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
        allergyStatusDTO = new AllergyStatusDTO(
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
        medicationUsageDTO = new MedicationUsageDTO(
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

        dailyDiscomfortDTO = new DailyDiscomfortDTO(
            medicalChecklist.getIsExperiencingSleepDifficulty(),
            medicalChecklist.getIsExperiencingStress(),
            medicalChecklist.getIsExperiencingMemoryLoss(),
            medicalChecklist.getIsExperiencingSensitivity(),
            medicalChecklist.getIsExperiencingDepression()
        );

        medicalChecklistDTO.setReproductiveHealthDTO(reproductiveHealthDTO);
        medicalChecklistDTO.setHealthConditionDTO(healthConditionDTO);
        medicalChecklistDTO.setAllergyStatusDTO(allergyStatusDTO);
        medicalChecklistDTO.setMedicationUsageDTO(medicationUsageDTO);
        medicalChecklistDTO.setDailyDiscomfortDTO(dailyDiscomfortDTO);

        return medicalChecklistDTO;
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



	public void changeNutrientDetails() {

        NutrientEntity nutrient = nutrientRepository.findNutrientEntityById(0L)
            .orElse(null);

        if(nutrient == null){
            throw new BaseException(BaseResponseStatus.DATABASE_ERROR);
        }


        NutrientDetails details = new NutrientDetails(
            false,
            List.of("수면의 질 개선", "생체 리듬 조절", "인지 기능 보호"),
            List.of("멜라토닌은 송과선에서 생성되는 호르몬으로, 생체 리듬과 수면-각성 주기를 조절", "수면 유도: 어두운 환경에서 분비량이 증가하여 자연스럽게 수면을 유도", "항산화 작용: 뇌에서 활성산소를 제거하고 신경세포를 보호하여 기억력과 인지 기능을 유지하는 데 기여", "스트레스 완화: 코르티솔 수치를 조절하여 심리적 스트레스를 줄이고 뇌 건강을 지원", "노화로 인한 인지 기능 저하 예방: 산화 스트레스를 줄이고 신경 손상을 예방하여 알츠하이머병과 같은 신경퇴행성 질환의 위험을 감소"),
            List.of("1일 0.3~1mg", "취침 30분~1시간 전에 복용 권장"),
            List.of("두통, 어지러움, 메스꺼움, 신경과민, 무력감, 비정상적인 꿈, 가슴 통증, 피부염, 졸음, 일시적인 우울감 등 발생 가능", "고용량 섭취 시 일시적인 수면 주기 혼란 또는 졸림 지속 가능", "드물게 발작, 자가면역 질환 악화, 생식력 감소 등 발생 가능"),
            List.of("임신/수유: 임산부와 수유부에 대한 안전성 연구가 부족하므로 섭취 전 전문가 상담 권장", "6개월 내 자녀 계획: 생식 건강에 대한 부정적 영향은 보고되지 않았으나, 섭취 전 전문가 상담 권장", "폐경기: 수면의 질 개선과 항산화 효과로 뇌 건강 유지에 도움", "흡연: 흡연으로 인해 멜라토닌 대사가 빨라질 수 있으므로 충분한 섭취 필요"),
            List.of("멜라토닌 또는 보충제 첨가물에 민감한 사람은 주의"),
            List.of("항응고제(출혈 위험 증가 가능)", "면역억제제(작용 상쇄 가능성)", "항우울제 및 항정신병 약물(과도한 졸음 유발 가능)", "수면유도제", "진정제", "항전간제(카르바마제핀), 항결핵제(리팜피신)"),
            List.of("간 질환(멜라토닌 대사 저하 가능)", "우울증(증상 악화 가능성)", "자가면역질환(면역 반응에 영향을 줄 수 있음)", "당뇨", "심장질환"),
            List.of("멜라토닌은 생체 리듬을 조절하고 수면의 질을 개선하는 데 매우 효과적이며, 뇌에서 항산화 작용을 통해 신경세포를 보호합니다. 특히 스트레스나 노화로 인한 인지 저하 예방에 도움을 줄 수 있습니다. 다만, 항우울제, 면역억제제, 항응고제를 복용 중이라면 섭취 전 전문가 상담을 권장합니다. 취침 전에 정확한 용량으로 복용하여 효과를 극대화할 수 있습니다.")
        );


        nutrient.setDetailsFromObject(details);
        nutrientRepository.save(nutrient);

    }
}
