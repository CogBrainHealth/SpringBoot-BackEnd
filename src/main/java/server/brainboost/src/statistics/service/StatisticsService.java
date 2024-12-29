package server.brainboost.src.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.config.TypeName;
import server.brainboost.src.statistics.dto.MyGameStatisticsDTO;
import server.brainboost.src.statistics.entity.UserStatisticsEntity;
import server.brainboost.src.statistics.repository.UserStatisticsRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    public MyGameStatisticsDTO getMyGameStatistics(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        List<UserStatisticsEntity> userStatisticsEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        MyGameStatisticsDTO myGameStatisticsDTO = new MyGameStatisticsDTO();
        int totalScore = 0;
        for(UserStatisticsEntity userStatistics : userStatisticsEntityList){
            if(userStatistics == null){
                continue;
            }

            if(userStatistics.getCount() == 0){
                continue;
            }


            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getTypeName() == TypeName.ATTENTION){

                myGameStatisticsDTO.setAttentionScore(score);
                totalScore += score;
            }else if(userStatistics.getGameType().getTypeName() == TypeName.SPATIAL_PERCEPTION){

                myGameStatisticsDTO.setSpatialPerceptionScore(score);
                totalScore += score;
            }
            else if(userStatistics.getGameType().getTypeName() == TypeName.MEMORY){

                myGameStatisticsDTO.setMemoryScore(score);
                totalScore += score;
            }
        }

        //totalScore 계산 로직 추가하기
        myGameStatisticsDTO.setTotalScore(totalScore);
        return myGameStatisticsDTO;


    }
}
