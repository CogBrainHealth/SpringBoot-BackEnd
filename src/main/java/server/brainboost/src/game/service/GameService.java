package server.brainboost.src.game.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.config.Status;
import server.brainboost.src.game.dto.*;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.entity.GameTypeEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.statistics.entity.UserStatisticsEntity;
import server.brainboost.src.user.entity.UserEntity;
import server.brainboost.src.user.entity.UserRecordEntity;
import server.brainboost.src.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;


    public GamePageDTO getGamePage(Long userId) {

        GamePageDTO gamePageDTO = new GamePageDTO();

        List<GameEntity> gameEntityList = gameRepository.findGames(Status.ACTIVE);

        for(int i =0; i<gameEntityList.size() ; i++){
            GameEntity game = gameEntityList.get(i);
            GameDetailsDTO gameDetailsDTO = new GameDetailsDTO(game.getGameId(), game.getName(), game.getImgUrl(), game.getDescription(), game.getGameType().getTypeName());

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
    public void saveMapNavigationResult(Long userId, MapNavigationResultDTO mapNavigationResultDTO) {

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

        // 1번
        UserRecordEntity userRecord = new UserRecordEntity(mapNavigationResultDTO.getScore(), user, game);

        // 2번
        //UserStatisticsEntity userStatistics = new UserStatisticsEntity()
    }

    @Transactional
    public void saveScroopTestResult(Long userId, ScroopTestResultDTO scroopTestResultDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 attention 값을 찾기
        // 3. statistics 작성 -> DB에서 attention 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

    }


    public void saveMentalRotationResult(Long userId, MentalRotationDTO mentalRotationDTO) {

        UserEntity user = userRepository.findUserEntityByUserIdAndStatus(userId, Status.ACTIVE)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.USER_NO_EXIST));

        // 저장할 내용
        // 1. user record 작성 -> score 점수 작성
        // 2. user statitics 작성 -> DB에서 memory 값을 찾기
        // 3. statistics 작성 -> DB에서 memory 값을 같이 찾기
        // 4. game record 작성 -> row data 그대로 작성

    }
}
