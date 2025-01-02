package server.brainboost.src.medical.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.medical.dto.MedicalChecklistDTO;
import server.brainboost.src.medical.entity.MedicalChecklistEntity;
import server.brainboost.src.medical.repository.MedicalChecklistRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class MedicalService {

    private final UserRepository userRepository;
    private final MedicalChecklistRepository medicalChecklistRepository;



    public void createMedicalCheckList(Long userId, MedicalChecklistDTO medicalChecklistDTO) throws BaseException{

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        MedicalChecklistEntity medicalChecklist = user.getMedicalChecklist();

        // 기존에 건강 체크 리스트를 작성하지 않은 경우
        if(medicalChecklist == null){

            if(user.getGender().equals('M') || user.getGender().equals('W')){
                medicalChecklist = new MedicalChecklistEntity(medicalChecklistDTO, user);
                user.setMedicalChecklist(medicalChecklist);
            }else{
                throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
            }
        }

        // 기존에 건강 체크 리스트가 작성된 경우
        else{

            if(user.getGender().equals('M') || user.getGender().equals('W')){
                medicalChecklist.updateMedicalChecklist(medicalChecklistDTO);
            }else{
                throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
            }
        }

        medicalChecklistRepository.save(medicalChecklist);
        return;
    }
}
