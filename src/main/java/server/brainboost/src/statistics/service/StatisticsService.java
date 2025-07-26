package server.brainboost.src.statistics.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.config.Status;
import server.brainboost.enums.*;
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

            if(userStatistics.getCognitiveDomain() == CognitiveDomain.ATTENTION){

                gameStatisticsDTO.setAttentionScore(score);
                totalScore += score;
            }else if(userStatistics.getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION){

                gameStatisticsDTO.setSpatialPerceptionScore(score);
                totalScore += score;
            }
            else if(userStatistics.getCognitiveDomain() == CognitiveDomain.MEMORY){

                gameStatisticsDTO.setMemoryScore(score);
                totalScore += score;
            }
        }

        //totalScore 계산 로직 추가하기
        gameStatisticsDTO.setTotalScore(totalScore);
        return gameStatisticsDTO;


    }

    //HACK 추후 나눗셈 처리 및 반복문을 stream으로 변경
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

            if(userStatistics.getCognitiveDomain() == CognitiveDomain.ATTENTION){

                statisticsHomeResponseDTO.setAttentionScore(score);
                totalScore += score;
            }else if(userStatistics.getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION){

                statisticsHomeResponseDTO.setSpatialPerceptionScore(score);
                totalScore += score;
            }
            else if(userStatistics.getCognitiveDomain() == CognitiveDomain.MEMORY){

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
    public StatisticResponse.AttentionScoreResponseDTO getAttentionScoreByUser(Long userId) {
        // 1) 사용자 존재 확인
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        // 2) 해당 유저의 모든 카테고리별 통계 조회
        List<CategoryScoreEntity> stats = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        long totalScore = 0;
        long count = 0;

        // 3) ATTENTION 영역만 골라서 평균 점수 계산
        for (CategoryScoreEntity s : stats) {
            if (s == null || s.getCount() == 0) continue;
            if (s.getCognitiveDomain() == CognitiveDomain.ATTENTION) {
                long avgLong = s.getTotalScore() / s.getCount();          // long ÷ long -> long
                int avg = Math.toIntExact(avgLong);                       // 안전하게 int 로 변환
                totalScore += avg;
                count++;
            }
        }

        if (count == 0) {
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        // 4) 누적한 평균들의 평균을 최종 avgScore 로 계산
        int finalAvg = Math.toIntExact(totalScore / count);

        AttentionScoreLevel level = AttentionScoreLevel.of(finalAvg);
        String message = level.getMessage();

        return new StatisticResponse.AttentionScoreResponseDTO(finalAvg, message);
    }

    public StatisticResponse.MemoryScoreResponseDTO getMemoryScoreByUser(Long userId) {
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> stats = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        long totalScore = 0;
        long count = 0;

        for (CategoryScoreEntity s : stats) {
            if (s == null || s.getCount() == 0) continue;
            if (s.getCognitiveDomain() == CognitiveDomain.MEMORY) {
                long avgLong = s.getTotalScore() / s.getCount();
                int avg = Math.toIntExact(avgLong);
                totalScore += avg;
                count++;
            }
        }

        if (count == 0) {
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        int finalAvg = Math.toIntExact(totalScore / count);

        MemoryScoreLevel level = MemoryScoreLevel.of(finalAvg);
        String message = level.getMessage();

        return new StatisticResponse.MemoryScoreResponseDTO(finalAvg, message);
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
        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NO_EXIST));

        List<CategoryScoreEntity> stats = userStatisticsRepository.findUserStatisticsEntitiesByUser(user);

        long totalScore = 0;
        long count = 0;

        for (CategoryScoreEntity s : stats) {
            if (s == null || s.getCount() == 0) continue;
            if (s.getCognitiveDomain() == CognitiveDomain.SPATIAL_PERCEPTION) {
                long avgLong = s.getTotalScore() / s.getCount();
                int avg = Math.toIntExact(avgLong);
                totalScore += avg;
                count++;
            }
        }

        if (count == 0) {
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        int finalAvg = Math.toIntExact(totalScore / count);

        SpatialPerceptionScoreLevel level = SpatialPerceptionScoreLevel.of(finalAvg);
        String message = level.getMessage();

        return new StatisticResponse.SpatialPerceptionScoreResponseDTO(finalAvg, message);
    }

   /* public MyCognitiveDomainDTO getMyCertainDomainStatistics(Long userId, CognitiveDomain cognitiveDomain) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
            .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));


    }*/
}
