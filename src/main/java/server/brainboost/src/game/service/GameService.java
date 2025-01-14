package server.brainboost.src.game.service;

import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.config.CognitiveDomain;
import server.brainboost.src.game.dto.*;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.game.entity.TodayGameEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.game.repository.GameTypeRepository;
import server.brainboost.src.game.repository.TodayGameRepository;
import server.brainboost.src.statistics.entity.GlobalStatisticsEntity;
import server.brainboost.src.statistics.entity.UserStatisticsEntity;
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

    public GamePageDTO getGamePage(Long userId) {

        GamePageDTO gamePageDTO = new GamePageDTO();

        List<GameEntity> gameEntityList = gameRepository.findGames(Status.ACTIVE);

        for(int i =0; i<gameEntityList.size() ; i++){
            GameEntity game = gameEntityList.get(i);
            GameDetailsDTO gameDetailsDTO = new GameDetailsDTO(game.getGameId(), game.getName(), game.getImgUrl(), game.getDescription(), game.getGameType().getCognitiveDomain());

            long typeId = game.getGameType().getGameTypeId();

            //TypeA
            if(typeId == 1){
                gamePageDTO.getTypeAList().add(gameDetailsDTO);
            }
            //TypeB
            else if(typeId == 2){
                gamePageDTO.getTypeBList().add(gameDetailsDTO);
            }
            //TypeC
            else if(typeId == 3){
                gamePageDTO.getTypeCList().add(gameDetailsDTO);
            }
        }

        return gamePageDTO;

    }

    @Transactional
    public GlobalStatisticsEntity updateGlobalStatistics(GameTypeEntity gameType, Long newScore) {
        int retryCount = 3;
        GlobalStatisticsEntity globalStatistics = null;

        while (retryCount > 0) {
            try {
                globalStatistics = globalStatisticsRepository.findStatisticsEntityByGameType(gameType)
                        .orElse(null);

                if(globalStatistics == null){
                    // 현재 지도보고 길찾기 게임 플레이 내용으로 새로운 globalStatistics 생성
                    globalStatistics = new GlobalStatisticsEntity(newScore.longValue(), 1L, gameType);
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
    public UserStatisticsEntity updateUserStatistics(UserEntity user, GameTypeEntity gameType, Long newScore){
        // 2번
        UserStatisticsEntity userStatistics = userStatisticsRepository.findUserStatisticsEntityByUserAndGameType(user, gameType)
                .orElse(null);

        if(userStatistics == null){
            // 현재 지도보고 길찾기 게임 플레이 내용으로 새로운 userStatistics 생성
            userStatistics = new UserStatisticsEntity(newScore, 1L, user, gameType);
        }else{
            userStatistics.updateUserStatisticEntity(newScore, 1L);
        }

        return userStatistics;
    }

    public UserRecordEntity saveUserRecord(UserEntity user, GameEntity game, int newScore){
        // 1번
        UserRecordEntity userRecord = new UserRecordEntity(newScore, user, game);
        return userRecord;
    }

    @Transactional
    public void saveMapNavigationResult(Long userId, MapNavigationResultDTO mapNavigationResultDTO) {

        String gameName = "지도보고 길찾기";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_NO_EXIST)));

        GameTypeEntity gameType = gameTypeRepository.findGameTypeEntityByCognitiveDomain(CognitiveDomain.SPATIAL_PERCEPTION)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_TYPE_NO_EXIST)));

        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 spatial_perception 값을 찾기
        // 3. statistics 작성 -> DB에서 spatial_perception 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

        //1번
        UserRecordEntity userRecord = saveUserRecord(user, game, mapNavigationResultDTO.getScore());

        // 2번
        UserStatisticsEntity userStatistics = updateUserStatistics(user, gameType, mapNavigationResultDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(gameType, mapNavigationResultDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);
    }

    @Transactional
    public void saveScroopTestResult(Long userId, ScroopTestResultDTO scroopTestResultDTO) {

        String gameName = "scroop test";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new BaseException(BaseResponseStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_NO_EXIST)));

        GameTypeEntity gameType = gameTypeRepository.findGameTypeEntityByCognitiveDomain(CognitiveDomain.ATTENTION)
                .orElseThrow(()->new BaseException((BaseResponseStatus.GAME_TYPE_NO_EXIST)));

        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 ATTENTION 값을 찾기
        // 3. statistics 작성 -> DB에서 ATTENTION 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

        //1번
        UserRecordEntity userRecord = saveUserRecord(user, game,scroopTestResultDTO.getScore());

        // 2번
        UserStatisticsEntity userStatistics = updateUserStatistics(user, gameType, scroopTestResultDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(gameType, scroopTestResultDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);

    }


    public void saveMentalRotationResult(Long userId, MentalRotationDTO mentalRotationDTO) {

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
        UserStatisticsEntity userStatistics = updateUserStatistics(user, gameType, mentalRotationDTO.getScore().longValue());

        //3번
        GlobalStatisticsEntity globalStatistics = updateGlobalStatistics(gameType, mentalRotationDTO.getScore().longValue());

        //4번
        //TODO: row data 받은 내용 추후에 추가

        userRecordRepository.save(userRecord);
        userStatisticsRepository.save(userStatistics);
        globalStatisticsRepository.save(globalStatistics);

    }

    public TodayGameDTO getTodayGame() {

        TodayGameEntity todayGame = todayGameRepository.findByDateWithGameAndGameType(LocalDate.now())
            .orElse(null);

        TodayGameDTO todayGameDTO;

        if (todayGame != null) {
            // 오늘 게임 엔티티가 존재할 때 처리할 로직
            todayGameDTO = new TodayGameDTO(
                todayGame.getGame().getGameId(),
                todayGame.getGame().getName(),
                todayGame.getGame().getImgUrl(),
                todayGame.getGame().getDescription(),
                todayGame.getGame().getVersion(),
                todayGame.getGame().getGameType().getCognitiveDomain()
            );

        } else {
            // 오늘 게임 엔티티가 존재하지 않을 때 처리할 로직
            todayGameDTO = new TodayGameDTO(
                null,
                null,
                null,
                null,
                null,
                null
            );

        }

        return todayGameDTO;


    }
}
