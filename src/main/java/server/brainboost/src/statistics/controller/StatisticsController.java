package server.brainboost.src.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.statistics.dto.StatisticResponse;
import server.brainboost.src.statistics.service.StatisticsService;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final UserService userService;
    private final StatisticsService statisticsService;

    @GetMapping("/api/statistics/score")
    @Operation(summary = "총합 점수 및 각 영역 점수 조회 api", description = "전체 점수 + 주의력 영역 점수 + 공간지각능력 점수 + 기억력 점수", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<StatisticResponse.GameStatisticsDTO>> getMyGameStatistics(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return ResponseEntity.ok(new BaseResponse<>(statisticsService.getMyGameStatistics(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }

    /*@GetMapping("/api/statistics/{cognitive_domain}")
    @Operation(summary = "해당 영역의 점소 보기 api", description = "MyGameScoreDTO에서 정보 가져오기", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<MyCognitiveDomainDTO> getMyCertainDomainStatistics(@PathVariable("cognitive_domain")CognitiveDomain cognitiveDomain){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(statisticsService.getMyCertainDomainStatistics(userId, cognitiveDomain));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }*/




}
