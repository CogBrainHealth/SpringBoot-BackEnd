package server.brainboost.src.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.config.Status;
import server.brainboost.enums.AttentionScoreLevel;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.enums.MemoryScoreLevel;
import server.brainboost.enums.TotalScoreLevel;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.statistics.dto.StatisticResponse;
import server.brainboost.src.statistics.entity.CategoryScoreEntity;
import server.brainboost.src.statistics.repository.UserStatisticsRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;
    private final UserStatisticsRepository userStatisticsRepository;

    public StatisticResponse.GameStatisticsDTO getMyGameStatistics(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        StatisticResponse.GameStatisticsDTO gameStatisticsDTO = new StatisticResponse.GameStatisticsDTO();
        int totalScore = 0;
        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.ATTENTION){

                gameStatisticsDTO.setAttentionScore(score);
                totalScore += score;
            }else if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION){

                gameStatisticsDTO.setSpatialPerceptionScore(score);
                totalScore += score;
            }
            else if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.MEMORY){

                gameStatisticsDTO.setMemoryScore(score);
                totalScore += score;
            }
        }

        //totalScore 계산 로직 추가하기
        gameStatisticsDTO.setTotalScore(totalScore);
        return gameStatisticsDTO;


    }

    public StatisticResponse.TotalScoreResponseDTO getMyTotalScore(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        int totalScore = 0;
        int count = 0;

        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());
            totalScore += score;
            count++;

        }

        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        totalScore = totalScore / count;

        TotalScoreLevel level = TotalScoreLevel.of(totalScore);
        String message = level.getMessage();

        return new StatisticResponse.TotalScoreResponseDTO(totalScore, message);

    }

    public StatisticResponse.StatisticsHomeResponseDTO getStatisticsByUser(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        StatisticResponse.StatisticsHomeResponseDTO statisticsHomeResponseDTO = new StatisticResponse.StatisticsHomeResponseDTO();

        int totalScore = 0;
        int count = 0;
        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.ATTENTION){

                statisticsHomeResponseDTO.setAttentionScore(score);
                totalScore += score;
            }else if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION){

                statisticsHomeResponseDTO.setSpatialPerceptionScore(score);
                totalScore += score;
            }
            else if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.MEMORY){

                statisticsHomeResponseDTO.setMemoryScore(score);
                totalScore += score;
            }

            count++;

        }
        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        totalScore = totalScore / count;
        statisticsHomeResponseDTO.setTotalScore(totalScore);

        return statisticsHomeResponseDTO;
    }

    public StatisticResponse.AttentionScoreResponseDTO getAttentionScoreByUser(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        int totalScore = 0;
        int count = 0;

        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.ATTENTION){
                totalScore += score;
                count++;
                break;
            }

        }
        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        AttentionScoreLevel level = AttentionScoreLevel.of(totalScore);
        String message = level.getMessage();


        return new StatisticResponse.AttentionScoreResponseDTO(totalScore, message);
    }

    public StatisticResponse.MemoryScoreResponseDTO getMemoryScoreByUser(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        int totalScore = 0;
        int count = 0;

        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.MEMORY){
                totalScore += score;
                count++;
                break;
            }

        }
        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        MemoryScoreLevel level = MemoryScoreLevel.of(totalScore);
        String message = level.getMessage();


        return new StatisticResponse.MemoryScoreResponseDTO(totalScore, message);
    }

    public StatisticResponse.SpatialPerceptionScoreResponseDTO getSpatialPerceptionScoreByUser(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> categoryScoreEntityList = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        int totalScore = 0;
        int count = 0;

        for(CategoryScoreEntity userStatistics : categoryScoreEntityList){
            if(userStatistics == null){
                continue;
            }

            // 아직 특정 영역의 점수 측정이 안 됐을 때
            if(userStatistics.getCount() == 0){
                continue;
            }

            int score = (int)(userStatistics.getTotalScore() / userStatistics.getCount());

            if(userStatistics.getGameType().getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION){
                totalScore += score;
                count++;
                break;
            }

        }
        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        MemoryScoreLevel level = MemoryScoreLevel.of(totalScore);
        String message = level.getMessage();


        return new StatisticResponse.SpatialPerceptionScoreResponseDTO(totalScore, message);
    }

   /* public MyCognitiveDomainDTO getMyCertainDomainStatistics(Long userId, CognitiveDomain cognitiveDomain) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));


    }*/
}
