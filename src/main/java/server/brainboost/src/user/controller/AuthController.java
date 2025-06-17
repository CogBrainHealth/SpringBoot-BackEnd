package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import server.brainboost.base.BaseException;
import server.brainboost.base.BaseResponse;
import server.brainboost.src.user.dto.AuthRequestDTO;
import server.brainboost.src.user.dto.AuthResponseDTO;
import server.brainboost.src.user.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //TODO scheduler을 통해 기간이 지난 refresh data 정리하기
    @PostMapping("/reissue")
    @Operation(summary = "새로 accessToken과 refreshToken을 발급해주는 api", description = "유효한 refreshToken을 가지고 있어야 작동", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 만료되었습니다"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 올바르지 않습니다")
    })
    public ResponseEntity<BaseResponse<AuthResponseDTO.TokenResponseDTO>> reissue(@Valid @RequestBody AuthRequestDTO.RefreshTokenRequestDTO refreshTokenRequestDTO){

        try{
            AuthResponseDTO.TokenResponseDTO tokenResponseDTO = authService.reissue(refreshTokenRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>(tokenResponseDTO));
        }catch (BaseException e) {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 api", description = "refresh 토큰 유효성 검사 후 refresh storage에서 토큰 삭제", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 만료되었습니다"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 올바르지 않습니다")
    })
    public ResponseEntity<BaseResponse<String>> logout(@Valid @RequestBody AuthRequestDTO.RefreshTokenRequestDTO refreshTokenRequestDTO){
        try{
            authService.logout(refreshTokenRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>("로그아웃에 성공했습니다"));
        }catch (BaseException e){
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }
    }


}
