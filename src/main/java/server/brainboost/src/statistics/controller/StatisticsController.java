package server.brainboost.src.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.code.ApiResponse;
import server.brainboost.src.statistics.dto.StatisticResponse;
import server.brainboost.src.statistics.service.StatisticsService;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final UserService userService;
    private final StatisticsService statisticsService;

    //TODO Test 해보기!!
    @GetMapping("/api/statistics/total")
    @Operation(summary = "점수 통계-합산 점수 조회 api", description = "전체 점수 출력", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<StatisticResponse.TotalScoreResponseDTO> getMyTotalScore(){

        Long userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.onSuccess(statisticsService.getMyTotalScore(userId));

    }

    //TODO Test 해보기!!
    @GetMapping("/api/statistics")
    @Operation(summary = "통계 영역의 기본 화면 api", description = "통계 영역 기본 화면", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<StatisticResponse.StatisticsHomeResponseDTO> getStatisticsByUser(){

        Long userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.onSuccess(statisticsService.getStatisticsByUser(userId));

    }

    //TODO Test 해보기!!
    @GetMapping("/api/statistics/attention")
    @Operation(summary = "점수 통계-주의력 점수 조회 api", description = "주의력 점수 및 설명", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<StatisticResponse.AttentionScoreResponseDTO> getAttentionScoreByUser(){

        Long userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.onSuccess(statisticsService.getAttentionScoreByUser(userId));

    }

    //TODO Test 해보기!!
    @GetMapping("/api/statistics/memory")
    @Operation(summary = "점수 통계-기억력 점수 조회 api", description = "기억력 점수 및 설명", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<StatisticResponse.MemoryScoreResponseDTO> getMemoryScoreByUser(){

        Long userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.onSuccess(statisticsService.getMemoryScoreByUser(userId));

    }

    //TODO Test 해보기!!
    @GetMapping("/api/statistics/spatial-perception")
    @Operation(summary = "점수 통계-공간지각능력 점수 조회 api", description = "공간지각능력 점수 및 설명", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<StatisticResponse.SpatialPerceptionScoreResponseDTO> getSpatialPerceptionScoreByUser(){

        Long userId = SecurityUtil.getCurrentUserId();
        return ApiResponse.onSuccess(statisticsService.getSpatialPerceptionScoreByUser(userId));

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
