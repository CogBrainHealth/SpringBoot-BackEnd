package server.brainboost.src.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.game.dto.GamePageDTO;
import server.brainboost.src.game.dto.MapNavigationResultDTO;
import server.brainboost.src.game.dto.MentalRotationDTO;
import server.brainboost.src.game.dto.ScroopTestResultDTO;
import server.brainboost.src.game.dto.TodayGameDTO;
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

    @PostMapping("/game/map-navigation/result")
    @Operation(summary = "지도보고 길찾기 결과 저장 api", description = "MapNavigationResultDTO", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> saveMapNavigationResult(@RequestBody @Valid MapNavigationResultDTO mapNavigationResultDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveMapNavigationResult(userId, mapNavigationResultDTO);
            return new BaseResponse<>("지도보고 길찾기 결과가 저장되었습니다.");
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/game/scroop-test/result")
    @Operation(summary = "scroop test 결과 저장 api", description = "scroopTestResultDTO", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> saveScroopTestResult(@RequestBody @Valid ScroopTestResultDTO scroopTestResultDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveScroopTestResult(userId, scroopTestResultDTO);
            return new BaseResponse<>("scroop test 결과가 저장되었습니다.");
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    @PostMapping("/game/mental-rotation/result")
    @Operation(summary = "mental rotation 결과 저장 api", description = "mentalRotationResultDTO", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> saveMentalRotationResult(@RequestBody @Valid MentalRotationDTO mentalRotationDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveMentalRotationResult(userId, mentalRotationDTO);
            return new BaseResponse<>("mental-rotation 결과가 저장되었습니다.");
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }

    @GetMapping("/api/game/today")
    @Operation(summary = "오늘의 게임 조회 api", description = "TodayGameDTO에 담긴 정보를 전달", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<TodayGameDTO> getTodayGame(){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(gameService.getTodayGame());
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }

}
