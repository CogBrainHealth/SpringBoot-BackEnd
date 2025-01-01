package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.user.dto.BasicInfoDTO;
import server.brainboost.src.user.dto.ProfileDTO;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/api/users/profile")
    @Operation(summary = "기본 정보 작성 api", description = "건강 ", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> setBasicInfo(@Valid @RequestBody BasicInfoDTO basicInfoDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            userService.setBasicInfo(userId, basicInfoDTO);
            return new BaseResponse<>("기본 정보가 작성되었습니다");
        }catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }

    }


    //TODO: builder 패턴 도입 하기
    @GetMapping("/api/users/{user_id}/profile")
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
