package server.brainboost.src.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.config.Status;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.src.statistics.dto.StatisticResponse;
import server.brainboost.src.statistics.entity.GameStatisticsEntity;
import server.brainboost.src.statistics.entity.enums.*;
import server.brainboost.src.statistics.repository.GameStatisticsRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final UserRepository userRepository;

    private final GameStatisticsRepository gameStatisticsRepository;
    private final GameRepository gameRepository;


    //HACK 추후 나눗셈 처리 및 반복문을 stream으로 변경
    public StatisticResponse.TotalScoreResponseDTO getMyTotalScore(Long userId) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<AgeGroup> input = new ArrayList<>();

        GameEntity scroopTest = gameRepository.findGameEntityByGameId(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));

        GameStatisticsEntity scroopTestStatistics = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, scroopTest)
                .orElse(null);

        input.add(scroopTestStatistics.getAgeGroup());

        GameEntity mapNavigation = gameRepository.findGameEntityByGameId(2L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));

        GameStatisticsEntity mapNavigationStatistics = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, mapNavigation)
                .orElse(null);

        input.add(mapNavigationStatistics.getAgeGroup());

        GameEntity mentalRotation = gameRepository.findGameEntityByGameId(3L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));

        GameStatisticsEntity mentalRotationStatistics = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, mapNavigation)
                .orElse(null);

        input.add(mentalRotationStatistics.getAgeGroup());

        AgeGroup ageGroup = AgeGroup.fromGroupList(input);

        return new StatisticResponse.TotalScoreResponseDTO(ageGroup, "각 영역에 대한 점수를 확인해주세요.");

    }



    /*public StatisticResponse.AttentionScoreResponseDTO getAttentionScoreByUser(Long userId) {

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
    }*/

    //TODO - 자바 stream 공부하면서 아래 로직을 stream 사용해서 바꿔보기!!
    public StatisticResponse.AttentionScoreResponseDTO getAttentionScoreByUser(Long userId) {
        // 1) 사용자 존재 확인
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByGameId(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));


        GameStatisticsEntity gameStatisticsEntity = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, game)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET));

        AttentionMessage attentionMessage = AttentionMessage.fromAgeGroup(gameStatisticsEntity.getAgeGroup());
        String message = attentionMessage.getMessage();

        return new StatisticResponse.AttentionScoreResponseDTO(gameStatisticsEntity.getAgeGroup(), message);
    }

    public StatisticResponse.MemoryScoreResponseDTO getMemoryScoreByUser(Long userId) {
        // 1) 사용자 존재 확인
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByGameId(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));


        GameStatisticsEntity gameStatisticsEntity = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, game)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET));


        MemoryMessage memoryMessage = MemoryMessage.fromAgeGroup(gameStatisticsEntity.getAgeGroup());
        String message = memoryMessage.getMessage();

        return new StatisticResponse.MemoryScoreResponseDTO(gameStatisticsEntity.getAgeGroup(), message);
    }

    /*public StatisticResponse.SpatialPerceptionScoreResponseDTO getSpatialPerceptionScoreByUser(Long userId) {

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

                *//** 가장 최근 점수만 얻고 싶으면 break 복구시키기**//*
                //break;
            }

        }
        if(count == 0){
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        SpatialPerceptionScoreLevel level = SpatialPerceptionScoreLevel.of(totalScore);
        String message = level.getMessage();


        return new StatisticResponse.SpatialPerceptionScoreResponseDTO(totalScore, message);
    }*/
    public StatisticResponse.SpatialPerceptionScoreResponseDTO getSpatialPerceptionScoreByUser(Long userId) {
        // 1) 사용자 존재 확인
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByGameId(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));


        GameStatisticsEntity gameStatisticsEntity = gameStatisticsRepository.findTopByUserAndGameOrderByCreateAtDesc(user, game)
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET));


        SpatialPerceptionMessage perceptionMessage = SpatialPerceptionMessage.fromAgeGroup(gameStatisticsEntity.getAgeGroup());
        String message = perceptionMessage.getMessage();

        return new StatisticResponse.SpatialPerceptionScoreResponseDTO(gameStatisticsEntity.getAgeGroup(), message);
    }

   /* public MyCognitiveDomainDTO getMyCertainDomainStatistics(Long userId, CognitiveDomain cognitiveDomain) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));


    }*/
}
