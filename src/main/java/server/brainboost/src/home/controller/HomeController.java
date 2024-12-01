package server.brainboost.src.home.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.home.dto.HomePageDTO;
import server.brainboost.src.home.service.HomeService;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class HomeController {

    private final HomeService homeService;

    @GetMapping("")
    @Operation(summary = "홈화면 api", description = "HomePageDTO에 담긴 정보를 전달", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<HomePageDTO> getHomePage(){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(homeService.getHomePage(userId));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }




}
