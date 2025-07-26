package server.brainboost.src.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.config.Status;
import server.brainboost.src.medical.entity.nutrient.enums.CognitiveDomain;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.game.dto.*;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.entity.TodayGameEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.game.repository.TodayGameRepository;
import server.brainboost.src.statistics.entity.GameStatisticsEntity;
import server.brainboost.src.statistics.repository.GameStatisticsRepository;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameStatisticsRepository gameStatisticsRepository;

    private final UserRepository userRepository;
    private final TodayGameRepository todayGameRepository;

    public GameResponseDTO.GetGamesResponseDTO getGames() {

        GameResponseDTO.GetGamesResponseDTO getGamesResponseDTO = new GameResponseDTO.GetGamesResponseDTO();

        List<GameEntity> gameEntityList = gameRepository.findGames(Status.ACTIVE);

        for(int i =0; i<gameEntityList.size() ; i++){
            GameEntity game = gameEntityList.get(i);
            GameResponseDTO.GameDetailsDTO gameDetailsDTO = new GameResponseDTO.GameDetailsDTO(game.getGameId(), game.getName(), game.getImgUrl(), game.getDescription(),game.getVersion() ,game.getCognitiveDomain());

            CognitiveDomain cognitiveDomain = game.getCognitiveDomain();

            //attention
            if(cognitiveDomain == CognitiveDomain.ATTENTION){
                getGamesResponseDTO.getAttentionGameList().add(gameDetailsDTO);
            }
            //spatial_perception
            else if(cognitiveDomain == CognitiveDomain.SPATIAL_PERCEPTION){
                getGamesResponseDTO.getSpatialPerceptionGameList().add(gameDetailsDTO);
            }
            //memory
            else if(cognitiveDomain == CognitiveDomain.MEMORY){
                getGamesResponseDTO.getMemoryGameList().add(gameDetailsDTO);
            }
        }

        return getGamesResponseDTO;

    }

    /*@Transactional
    public GlobalStatisticsEntity updateGlobalStatistics(CognitiveDomain cognitiveDomain, Long newScore) {
        int retryCount = 3;
        GlobalStatisticsEntity globalStatistics = null;

        while (retryCount > 0) {
            try {
                globalStatistics = null;

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
    }*/


    @Transactional
    public void saveMapNavigationResult(Long userId, GameRequestDTO.MapNavigationResultDTO mapNavigationResultDTO) {

        final String gameName = "지도보고 길찾기";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new GeneralException(ErrorStatus.GAME_NO_EXIST));

        //1번
        GameStatisticsEntity gameStatisticsEntity = new GameStatisticsEntity(mapNavigationResultDTO.getAgeGroup(), user, game);
        // 2번
        //GameDetailsStatisticsEntity 처리

        gameStatisticsRepository.save(gameStatisticsEntity);
    }

    @Transactional
    public void saveScroopTestResult(Long userId, GameRequestDTO.ScroopTestResultDTO scroopTestResultDTO) {

        final String gameName = "scroop test";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new GeneralException(ErrorStatus.GAME_NO_EXIST));


        //1번
        GameStatisticsEntity gameStatisticsEntity = new GameStatisticsEntity(scroopTestResultDTO.getAgeGroup(), user, game);
        // 2번
        //GameDetailsStatisticsEntity 처리

        gameStatisticsRepository.save(gameStatisticsEntity);

    }


    public void saveMentalRotationResult(Long userId, GameRequestDTO.MentalRotationDTO mentalRotationDTO) {

        String gameName = "mental rotation";

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(()->new GeneralException(ErrorStatus.USER_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByName(gameName)
                .orElseThrow(()->new GeneralException(ErrorStatus.GAME_NO_EXIST));


        //1번
        GameStatisticsEntity gameStatisticsEntity = new GameStatisticsEntity(mentalRotationDTO.getAgeGroup(), user, game);
        // 2번
        //GameDetailsStatisticsEntity 처리

        gameStatisticsRepository.save(gameStatisticsEntity);


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
