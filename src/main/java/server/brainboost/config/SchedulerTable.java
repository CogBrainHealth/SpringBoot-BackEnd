package server.brainboost.config;

import java.time.LocalDate;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.game.dto.inter.GameInterface;
import server.brainboost.src.game.entity.GameEntity;
import server.brainboost.src.game.repository.GameRepository;
import server.brainboost.src.game.entity.TodayGameEntity;
import server.brainboost.src.game.repository.TodayGameRepository;


//TODO 여러 대의 instance에서 중복 schedule을 피하는 로직 짜기
@Component
@RequiredArgsConstructor
public class SchedulerTable {

    private final TodayGameRepository todayGameRepository;
    private final GameRepository gameRepository;

    @Scheduled(cron = "0 0 0 * * ?")  // 매일 자정에 실행
    public void changeTodayGame(){
        GameInterface gameInterface = gameRepository.findGameInterfaceOrderByRandom()
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));

        GameEntity game = gameRepository.findGameEntityByGameId(gameInterface.getId())
                .orElseThrow(() -> new GeneralException(ErrorStatus.GAME_NO_EXIST));

        TodayGameEntity todayGame = new TodayGameEntity(LocalDate.now(), game);
        todayGameRepository.save(todayGame);

        System.out.println("오늘의 게임 변경");
    }


}
