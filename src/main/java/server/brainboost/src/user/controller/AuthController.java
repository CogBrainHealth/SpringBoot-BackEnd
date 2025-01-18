package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import server.brainboost.jwt.JWTUtil;
import server.brainboost.src.user.dto.RefreshTokenRequestDTO;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.src.user.dto.TokenResponseDTO;
import server.brainboost.src.user.service.AuthService;
import server.brainboost.utils.ResponseUtil;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    //TODO scheduler을 통해 기간이 지난 refresh data 정리하기
    @PostMapping("/reissue")
    @Operation(summary = "새로 accessToken과 refreshToken을 발급해주는 api", description = "유효한 refreshToken을 가지고 있어야 작동", responses = {
        @ApiResponse(responseCode = "200", description = "성공"),
        @ApiResponse(responseCode = "400", description = "파라미터 오류"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 만료되었습니다"),
        @ApiResponse(responseCode = "401", description = "refresh 토큰이 올바르지 않습니다")
    })
    public ResponseEntity<BaseResponse<TokenResponseDTO>> reissue(@Valid @RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO){

        try{
            TokenResponseDTO tokenResponseDTO = authService.reissue(refreshTokenRequestDTO);
            return ResponseEntity.ok(new BaseResponse<>(tokenResponseDTO));
        }catch (BaseException e) {
            HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
            return ResponseEntity.status(httpStatus).body(new BaseResponse<>(e.getStatus()));
        }

    }



}
