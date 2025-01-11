package server.brainboost.src.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import server.brainboost.base.BaseResponse;
import server.brainboost.src.user.dto.SignUpDTO;
import server.brainboost.src.user.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {

    //TODO 랜덤 닉네임 구현 생각해보기
    private final AuthService authService;


    @PostMapping("/signup")
    @Operation(summary = "임시 회원가입 api", description = "SignUpDto에 담긴 정보를 토대로 회원가입", responses = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "파라미터 오류"),
    })
    public BaseResponse<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO){

        authService.signUp(signUpDTO);

        return new BaseResponse<>("ok");
    }

    @GetMapping("/testing")
    public String testConfig(){
        return "성공";
    }

    @GetMapping("/hello")
    public String testHello(){
        return "성공";
    }

}
