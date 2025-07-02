package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.brainboost.code.ApiResponse;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.exception.GeneralException;
import server.brainboost.src.user.dto.*;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    @Operation(summary = "회원가입 api", description = "계정 생성", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류")
    })
    public ApiResponse<UserResponseDTO.UserIdResponseDTO> join(@Valid @RequestBody UserRequestDTO.JoinRequestDTO joinRequestDTO) {

        return ApiResponse.onSuccess(userService.join(joinRequestDTO));

    }

    @PatchMapping("/api/users/nickname")
    @Operation(summary = "닉네임 수정 api", description = "닉네임 정보를 수정하는 api ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<UserResponseDTO.UserIdResponseDTO> setNickname(@Valid @RequestBody UserRequestDTO.NicknameRequestDTO nicknameRequestDTO){

        Long userId = SecurityUtil.getCurrentUserId();

        userService.setNickName(userId, nicknameRequestDTO);
        return ApiResponse.onSuccess(new UserResponseDTO.UserIdResponseDTO(userId));

    }


    @PatchMapping("/api/users/gender")
    @Operation(summary = "성별 수정 api", description = "성별 정보를 수정하는 api ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<UserResponseDTO.UserIdResponseDTO> setGender(@Valid @RequestBody UserRequestDTO.GenderRequestDTO genderRequestDTO){

        Long userId = SecurityUtil.getCurrentUserId();

        userService.setGender(userId, genderRequestDTO);
        return ApiResponse.onSuccess(new UserResponseDTO.UserIdResponseDTO(userId));
    }


    @PatchMapping("/api/users/birthdate")
    @Operation(summary = "생년월일 수정 api", description = "생년월일 정보를 수정하는 api ", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<UserResponseDTO.UserIdResponseDTO> setBirthDate(@Valid @RequestBody UserRequestDTO.BirthDateRequestDTO birthDateRequestDTO){

        Long userId = SecurityUtil.getCurrentUserId();

        userService.setBirthDate(userId, birthDateRequestDTO);
        return ApiResponse.onSuccess(new UserResponseDTO.UserIdResponseDTO(userId));
    }



    //TODO: builder 패턴 도입 하기
    @GetMapping("/api/users/profile")
    @Operation(summary = "내 정보 보기 api", description = "내 정보 조회", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ApiResponse<UserResponseDTO.ProfileDTO> getProfile(){

        Long userId = SecurityUtil.getCurrentUserId();

        return ApiResponse.onSuccess(userService.getProfile(userId));
    }




}
