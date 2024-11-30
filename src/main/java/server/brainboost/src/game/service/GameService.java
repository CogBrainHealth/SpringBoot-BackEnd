package server.brainboost.src.game.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.brainboost.config.Status;
import server.brainboost.src.game.dto.GameDetailsDTO;
import server.brainboost.src.game.dto.GamePageDTO;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.repository.GameRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;


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
}
