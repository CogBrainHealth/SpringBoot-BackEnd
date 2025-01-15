package server.brainboost.src.game.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;


    //TODO 1. List<Entity> 로 받은 것들 DTO로 받는 방법 생각하기  2. fetch join이 필수인지 생각해보기 3.new arrayList
    @GetMapping("/api/game/info")
    @Operation(summary = "모든 게임 정보 조회 api", description = "모든 게임을 주의력, 공간지각능력, 기억력 부문으로 나눠 정보를 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다")
    })
    public ResponseEntity<BaseResponse<GamePageDTO>> getGamePage(){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return ResponseEntity.ok(new BaseResponse<>(gameService.getGamePage(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }

    @PostMapping("/api/game/map-navigation/result")
    @Operation(summary = "지도보고 길찾기 결과 저장 api", description = "게임 후 플레이 내용을 저장", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임 타입이 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<String>> saveMapNavigationResult(@RequestBody @Valid MapNavigationResultDTO mapNavigationResultDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveMapNavigationResult(userId, mapNavigationResultDTO);
            return ResponseEntity.ok(new BaseResponse<>("지도보고 길찾기 결과가 저장되었습니다."));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @PostMapping("/api/game/scroop-test/result")
    @Operation(summary = "scroop test 결과 저장 api", description = "게임 후 플레이 내용을 저장", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임 타입이 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<String>> saveScroopTestResult(@RequestBody @Valid ScroopTestResultDTO scroopTestResultDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveScroopTestResult(userId, scroopTestResultDTO);
            return ResponseEntity.ok(new BaseResponse<>("scroop test 결과가 저장되었습니다."));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @PostMapping("/api/game/mental-rotation/result")
    @Operation(summary = "mental rotation 결과 저장 api", description = "게임 후 플레이 내용을 저장", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임이 존재하지 않습니다"),
            @ApiResponse(responseCode = "500", description = "게임 타입이 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<String>> saveMentalRotationResult(@RequestBody @Valid MentalRotationDTO mentalRotationDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            gameService.saveMentalRotationResult(userId, mentalRotationDTO);
            return ResponseEntity.ok(new BaseResponse<>("mental-rotation 결과가 저장되었습니다."));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @GetMapping("/api/game/today")
    @Operation(summary = "오늘의 게임 조회 api", description = "매일 0시에 오늘의 게임이 바뀌며 오늘의 게임을 조회", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
    })
    public ResponseEntity<BaseResponse<TodayGameDTO>> getTodayGame(){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return ResponseEntity.ok(new BaseResponse<>(gameService.getTodayGame()));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }

}
