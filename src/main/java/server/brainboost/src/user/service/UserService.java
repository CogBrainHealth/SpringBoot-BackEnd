package server.brainboost.src.user.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.user.dto.BasicInfoDTO;
import server.brainboost.src.user.dto.JoinDTO;
import server.brainboost.src.user.dto.ProfileDTO;
import server.brainboost.src.user.dto.UserIdResponseDTO;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ProfileDTO getProfile(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        ProfileDTO profileDTO = new ProfileDTO(userId, user.getNickname(), user.getGender(), user.getBirthDate(), user.getProfileImgUrl(), user.getRole());

        return profileDTO;
    }

    public void setBasicInfo(Long userId, BasicInfoDTO basicInfoDTO){

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        user.setNickname(basicInfoDTO.getNickname());
        user.setGender(basicInfoDTO.getGender());
        user.setBirthDate(basicInfoDTO.getBirthDate());

        userRepository.save(user);
    }

    public UserIdResponseDTO join(@Valid JoinDTO joinDTO) throws BaseException {

        //TODO 에러 handler 및 로직 수정하기
        if(!(joinDTO.getGender().equals('W') || joinDTO.getGender().equals('M'))){
            throw new BaseException(BaseResponseStatus.UNEXPECTED_GENDER);
        }

       if(userRepository.existsAllByUsername(joinDTO.getUsername())) {
           throw new BaseException(BaseResponseStatus.USER_ALREADY_EXIST);
       }

        UserEntity userEntity;

        userEntity = new UserEntity(joinDTO);
        userEntity.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        userEntity.setIsPremium(Boolean.FALSE);
        userRepository.save(userEntity);

        return new UserIdResponseDTO(userEntity.getUserId());
    }
}
