package server.brainboost.src.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.user.dto.BasicInfoDTO;
import server.brainboost.src.user.dto.ProfileDTO;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    public ProfileDTO getProfile(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        ProfileDTO profileDTO = new ProfileDTO(userId, user.getNickname(), user.getGender(), user.getBirthDate(), user.getProfileImgUrl(), user.getRole());

        return profileDTO;
    }

    public void setBasicInfo(Long userId, BasicInfoDTO basicInfoDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        user.setNickname(basicInfoDTO.getNickname());
        user.setGender(basicInfoDTO.getGender());
        user.setBirthDate(basicInfoDTO.getBirthDate());

        userRepository.save(user);
    }
}
