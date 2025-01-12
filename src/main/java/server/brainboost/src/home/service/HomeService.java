package server.brainboost.src.home.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.home.dto.HomePageDTO;
import server.brainboost.src.game.entity.TodayGameEntity;
import server.brainboost.src.game.repository.TodayGameRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class HomeService {

    private final TodayGameRepository todayGameRepository;
    private final UserRepository userRepository;

    private final GameRepository gameRepository;
    public HomePageDTO getHomePage(Long userId) throws BaseException {

        HomePageDTO homePageDTO;

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        TodayGameEntity todayGame = todayGameRepository.findByTodayGameId(1L).orElse(null);  // 값이 없으면 null을 반환

        if (todayGame != null) {
            // 오늘 게임 엔티티가 존재할 때 처리할 로직
            homePageDTO = new HomePageDTO(
                    userId,
                    user.getNickname(),
                    user.getProfileImgUrl(),
                    todayGame.getGameId(),
                    todayGame.getName(),
                    todayGame.getImgUrl(),
                    todayGame.getDescription()
            );

        } else {
            // 오늘 게임 엔티티가 존재하지 않을 때 처리할 로직
            homePageDTO = new HomePageDTO(
                    userId,
                    user.getNickname(),
                    user.getProfileImgUrl(),
                    null,
                    null,
                    null,
                    null
            );

        }

        return homePageDTO;

    }

}
