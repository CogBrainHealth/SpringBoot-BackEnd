package server.brainboost.src.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        UserResponseDTO.ProfileDTO profileDTO = new UserResponseDTO.ProfileDTO(userId, user.getNickname(), user.getGender(), user.getBirthDate(), user.getProfileImgUrl(), user.getRole());

        return profileDTO;
    }


    public UserResponseDTO.UserIdResponseDTO join(@Valid UserRequestDTO.JoinRequestDTO joinRequestDTO) throws BaseException {

        //TODO 에러 handler 및 로직 수정하기
        if(!(joinRequestDTO.getGender().equals('W') || joinRequestDTO.getGender().equals('M'))){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
        }

       if(userRepository.existsAllByUsername(joinRequestDTO.getUsername())) {
           throw new BaseException(BaseResponseStatus.USER_ALREADY_EXIST);
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
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        user.setNickname(nicknameRequestDTO.getNickname());

        userRepository.save(user);

    }

    public void setGender(Long userId, UserRequestDTO.@Valid GenderRequestDTO genderRequestDTO) throws BaseException {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        user.updateGender(genderRequestDTO.getGender());

        userRepository.save(user);
    }

    public void setBirthDate(Long userId, UserRequestDTO.@Valid BirthDateRequestDTO birthDateRequestDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        user.setBirthDate(birthDateRequestDTO.getBirthDate());

        userRepository.save(user);
    }
}
