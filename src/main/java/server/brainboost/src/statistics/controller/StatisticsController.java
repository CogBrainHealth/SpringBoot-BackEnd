package server.brainboost.src.statistics.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.statistics.dto.MyGameStatisticsDTO;
import server.brainboost.src.statistics.service.StatisticsService;
import server.brainboost.src.user.dto.ProfileDTO;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final UserService userService;
    private final StatisticsService statisticsService;

    @GetMapping("/statistics")
    @Operation(summary = "내 점수 보기 api", description = "MyGameScoreDTO에서 정보 가져오기", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<MyGameStatisticsDTO> getMyGameStatistics(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(statisticsService.getMyGameStatistics(userId));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }





}
