package server.brainboost.src.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.config.Status;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.user.dto.*;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserResponseDTO.ProfileDTO getProfile(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        UserResponseDTO.ProfileDTO profileDTO = new UserResponseDTO.ProfileDTO(userId, user.getNickname(), user.getGender(), user.getBirthDate(), user.getProfileImgUrl(), user.getRole());

        return profileDTO;
    }


    public UserResponseDTO.UserIdResponseDTO join(@Valid UserRequestDTO.JoinRequestDTO joinRequestDTO){

        //TODO 에러 handler 및 로직 수정하기
        if(!(joinRequestDTO.getGender().equals('W') || joinRequestDTO.getGender().equals('M'))){
            throw new GeneralException(ErrorStatus.UNEXPECTED_GENDER);
        }

       if(userRepository.existsAllByUsername(joinRequestDTO.getUsername())) {
           throw new GeneralException(ErrorStatus.USER_ALREADY_EXIST);
       }

       if(joinRequestDTO.getIsPrivacy().equals(Boolean.FALSE)){
           throw new GeneralException(ErrorStatus.NEEDED_PRIVACY);
       }

        UserEntity userEntity;

        userEntity = new UserEntity(joinRequestDTO);
        userEntity.setPassword(bCryptPasswordEncoder.encode(joinRequestDTO.getPassword()));
        userEntity.setIsPremium(Boolean.FALSE);
        userRepository.save(userEntity);

        return new UserResponseDTO.UserIdResponseDTO(userEntity.getUserId());
    }

    public void setNickName(Long userId, UserRequestDTO.@Valid NicknameRequestDTO nicknameRequestDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        user.setNickname(nicknameRequestDTO.getNickname());

        userRepository.save(user);

    }

    public void setGender(Long userId, UserRequestDTO.@Valid GenderRequestDTO genderRequestDTO){

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        user.updateGender(genderRequestDTO.getGender());

        userRepository.save(user);
    }

    public void setBirthDate(Long userId, UserRequestDTO.@Valid BirthDateRequestDTO birthDateRequestDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        user.setBirthDate(birthDateRequestDTO.getBirthDate());

        userRepository.save(user);
    }

    public UserResponseDTO.checkMedicalResponseDTO checkMedical(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        return UserResponseDTO.checkMedicalResponseDTO.builder()
                .isMedicalTest(user.getIsMedicalTest())
                .build();

    }

    public UserResponseDTO.checkPremiumMedicalResponseDTO checkPremiumMedical(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        return UserResponseDTO.checkPremiumMedicalResponseDTO.builder()
                .isPremiumMedicalTest(user.getIsPremiumMedicalTest())
                .build();

    }
}
