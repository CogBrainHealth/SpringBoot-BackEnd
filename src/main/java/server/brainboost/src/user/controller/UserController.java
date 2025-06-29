package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.brainboost.exception.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.base.BaseResponseStatus;
import server.brainboost.src.user.dto.*;
import server.brainboost.src.user.service.UserService;
import server.brainboost.utils.SecurityUtil;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/join")
    @Operation(summary = "회원가입 api", description = "계정 생성", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류")
    })
    public ResponseEntity<BaseResponse<UserResponseDTO.UserIdResponseDTO>> join(@Valid @RequestBody UserRequestDTO.JoinRequestDTO joinRequestDTO) {

        try{
            return ResponseEntity.ok(new BaseResponse<>(userService.join(joinRequestDTO)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }


    @PatchMapping("/api/users/nickname")
    @Operation(summary = "닉네임 수정 api", description = "닉네임 정보를 수정하는 api ", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<UserResponseDTO.UserIdResponseDTO>> setNickname(@Valid @RequestBody UserRequestDTO.NicknameRequestDTO nicknameRequestDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            userService.setNickName(userId, nicknameRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>(new UserResponseDTO.UserIdResponseDTO(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @PatchMapping("/api/users/gender")
    @Operation(summary = "성별 수정 api", description = "성별 정보를 수정하는 api ", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<UserResponseDTO.UserIdResponseDTO>> setGender(@Valid @RequestBody UserRequestDTO.GenderRequestDTO genderRequestDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            userService.setGender(userId, genderRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>(new UserResponseDTO.UserIdResponseDTO(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @PatchMapping("/api/users/birthdate")
    @Operation(summary = "생년월일 수정 api", description = "생년월일 정보를 수정하는 api ", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<UserResponseDTO.UserIdResponseDTO>> setBirthDate(@Valid @RequestBody UserRequestDTO.BirthDateRequestDTO birthDateRequestDTO){
        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            userService.setBirthDate(userId, birthDateRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>(new UserResponseDTO.UserIdResponseDTO(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }



    //TODO: builder 패턴 도입 하기
    @GetMapping("/api/users/profile")
    @Operation(summary = "내 정보 보기 api", description = "내 정보 조회", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
            @ApiResponse(responseCode = "500", description = "로그인이 필요한 서비스 입니다"),
            @ApiResponse(responseCode = "500", description = "유저가 존재하지 않습니다"),
    })
    public ResponseEntity<BaseResponse<UserResponseDTO.ProfileDTO>> getProfile(){

        try{
            Long userId = SecurityUtil.getCurrentUserId()
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.REQUIRED_LOGIN));

            return ResponseEntity.ok(new BaseResponse<>(userService.getProfile(userId)));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }




}
