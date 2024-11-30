package server.brainboost.src.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.game.dto.GamePageDTO;
import server.brainboost.src.game.service.GameService;
import server.brainboost.src.home.dto.HomePageDTO;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    //TODO 1. List<Entity> 로 받은 것들 DTO로 받는 방법 생각하기  2. fetch join이 필수인지 생각해보기 3.new arrayList
    @GetMapping("/game")
    @Operation(summary = "훈련 기본화면 api", description = "GamePageDTO에 담긴 정보를 전달", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<GamePageDTO> getGamePage(){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(gameService.getGamePage(userId));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }


}
