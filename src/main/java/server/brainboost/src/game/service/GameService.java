package server.brainboost.src.game.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.enums.CognitiveDomain;
import server.brainboost.src.game.dto.*;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.game.entity.TodayGameEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.game.repository.GameTypeRepository;
import server.brainboost.src.game.repository.TodayGameRepository;
import server.brainboost.src.statistics.entity.GlobalStatisticsEntity;
import server.brainboost.src.statistics.entity.CategoryScoreEntity;
import server.brainboost.src.statistics.repository.GlobalStatisticsRepository;
import server.brainboost.src.statistics.repository.UserStatisticsRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.entity.UserRecordEntity;
import server.brainboost.src.user.repository.UserRecordRepository;
import server.brainboost.src.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;
    private final UserStatisticsRepository userStatisticsRepository;
    private final GameTypeRepository gameTypeRepository;
    private final GlobalStatisticsRepository globalStatisticsRepository;
    private final UserRecordRepository userRecordRepository;
    private final TodayGameRepository todayGameRepository;

    public GameResponseDTO.GameByCognitionDTO getGamePage(Long userId) {

        GameResponseDTO.GameByCognitionDTO gameByCognitionDTO = new GameResponseDTO.GameByCognitionDTO();

        List<GameEntity> gameEntityList = gameRepository.findGames(Status.ACTIVE);

        for(int i =0; i<gameEntityList.size() ; i++){
            GameEntity game = gameEntityList.get(i);
            GameResponseDTO.GameDetailsDTO gameDetailsDTO = new GameResponseDTO.GameDetailsDTO(game.getGameId(), game.getName(), game.getImgUrl(), game.getDescription(),game.getVersion() ,game.getCognitiveDomain());

            CognitiveDomain cognitiveDomain = game.getCognitiveDomain();

            //attention
            if(cognitiveDomain == CognitiveDomain.ATTENTION){
                gameByCognitionDTO.getAttentionGameList().add(gameDetailsDTO);
            }
            //spatial_perception
            else if(cognitiveDomain == CognitiveDomain.SPATIAL_PERCEPTION){
                gameByCognitionDTO.getSpatialPerceptionGameList().add(gameDetailsDTO);
            }
            //memory
            else if(cognitiveDomain == CognitiveDomain.MEMORY){
                gameByCognitionDTO.getMemoryGameList().add(gameDetailsDTO);
            }
        }

        return gameByCognitionDTO;

    }

    @Transactional
    public GlobalStatisticsEntity updateGlobalStatistics(CognitiveDomain cognitiveDomain, Long newScore) {
        int retryCount = 3;
        GlobalStatisticsEntity globalStatistics = null;

        while (retryCount > 0) {
            try {
                globalStatistics = globalStatisticsRepository.findStatisticsEntityByCognitiveDomain(cognitiveDomain)
                        .orElse(null);

                if(globalStatistics == null){
                    // 현재 지도보고 길찾기 게임 플레이 내용으로 새로운 globalStatistics 생성
                    globalStatistics = new GlobalStatisticsEntity(newScore.longValue(), 1L, cognitiveDomain);
                }else{
                    globalStatistics.updateGlobalStatisticsEntity(newScore.longValue(), 1L);
                }

                break;
            } catch (OptimisticLockException e) {
                retryCount--;
                if (retryCount == 0) {
                    throw new BaseException(BaseResponseStatus.DATA_CONFLICT);
                }
            }
        }
        return globalStatistics; // 성공적으로 저장했으면 종료
    }

    @Transactional
    public CategoryScoreEntity updateUserStatistics(UserEntity user, CognitiveDomain cognitiveDomain, Long newScore){
        // 2번
        CategoryScoreEntity userStatistics = userStatisticsRepository.findUserStatisticsEntityByUserAndCognitiveDomain(user, cognitiveDomain)
                .orElse(null);

        if(userStatistics == null){
            // 현재 지도보고 길찾기 게임 플레이 내용으로 새로운 userStatistics 생성
            userStatistics = new CategoryScoreEntity(newScore, 1L, user, cognitiveDomain);
        }else{
            userStatistics.updateCategoryScoreEntity(newScore, 1L);
        }

        return userStatistics;
    }

    public UserRecordEntity saveUserRecord(UserEntity user, GameEntity game, int newScore){
        // 1번
        UserRecordEntity userRecord = new UserRecordEntity(newScore, user, game);
        return userRecord;
    }

    @Transactional
    public void saveMapNavigationResult(Long userId, GameRequestDTO.MapNavigationResultDTO mapNavigationResultDTO) {

        String gameName = "지도보고 길찾기";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_NO_EXIST)));


        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 spatial_perception 값을 찾기
        // 3. statistics 작성 -> DB에서 spatial_perception 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

        //1번
        UserRecordEntity userRecord = saveUserRecord(user, game, mapNavigationResultDTO.getScore());

        // 2번
        CategoryScoreEntity userStatistics = updateUserStatistics(user, game.getCognitiveDomain(), mapNavigationResultDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(game.getCognitiveDomain(), mapNavigationResultDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);
    }

    @Transactional
    public void saveScroopTestResult(Long userId, GameRequestDTO.ScroopTestResultDTO scroopTestResultDTO) {

        String gameName = "scroop test";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_NO_EXIST)));


        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 ATTENTION 값을 찾기
        // 3. statistics 작성 -> DB에서 ATTENTION 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

        //1번
        UserRecordEntity userRecord = saveUserRecord(user, game,scroopTestResultDTO.getScore());

        // 2번
        CategoryScoreEntity userStatistics = updateUserStatistics(user, game.getCognitiveDomain(), scroopTestResultDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(game.getCognitiveDomain(), scroopTestResultDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);

    }


    public void saveMentalRotationResult(Long userId, GameRequestDTO.MentalRotationDTO mentalRotationDTO) {

        String gameName = "mental rotation";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_NO_EXIST)));

        GameTypeEntity gameType = gameTypeRepository.findGameTypeEntityByCognitiveDomain(CognitiveDomain.MEMORY)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_TYPE_NO_EXIST)));

        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 spatial_perception 값을 찾기
        // 3. statistics 작성 -> DB에서 spatial_perception 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

        //1번
        UserRecordEntity userRecord = saveUserRecord(user, game, mentalRotationDTO.getScore());

        // 2번
        CategoryScoreEntity userStatistics = updateUserStatistics(user, game.getCognitiveDomain(), mentalRotationDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(game.getCognitiveDomain(), mentalRotationDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);

    }

    public GameResponseDTO.GameDetailsDTO getTodayGame() {

        TodayGameEntity todayGame = todayGameRepository.findTopByOrderByCreateAtAsc()
            .orElse(null);

        GameResponseDTO.GameDetailsDTO gameDetailsDTO;

        if (todayGame != null) {
            // 오늘 게임 엔티티가 존재할 때 처리할 로직
            gameDetailsDTO = new GameResponseDTO.GameDetailsDTO(
                todayGame.getGame().getGameId(),
                todayGame.getGame().getName(),
                todayGame.getGame().getImgUrl(),
                todayGame.getGame().getDescription(),
                todayGame.getGame().getVersion(),
                todayGame.getGame().getCognitiveDomain()
            );

        } else {
            // 오늘 게임 엔티티가 존재하지 않을 때 처리할 로직
            gameDetailsDTO = new GameResponseDTO.GameDetailsDTO(
                null,
                null,
                null,
                null,
                null,
                null
            );

        }

        return gameDetailsDTO;


    }
}
