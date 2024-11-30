package server.brainboost.src.user.controller;

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
import server.brainboost.src.user.dto.ProfileDTO;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //TODO: builder 패턴 도입 하기
    @GetMapping("/profile")
    @Operation(summary = "내 정보 보기 api", description = "profileDTO에 담긴 정보를 가져오기", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<ProfileDTO> getProfile(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return new BaseResponse<>(userService.getProfile(userId));
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }




}
