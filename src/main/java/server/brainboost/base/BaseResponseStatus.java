package server.brainboost.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    //요청 성공
    Success(true, 200, "ok"),

    UNEXPECTED_ERROR(false, 1000,"unexpected error"),
    //user 오류 -> 2000번
    USER_NO_EXIST(false, 2000, "user is not exist"),
    REQUIRED_LOGIN(false, 2100, "로그인이 필요한 서비스 입니다"),

    //game 오류 -> 3000번
    GAME_NO_EXIST(false, 3000, "game is not exist"),
    TODAY_GAME_NO_EXIST(false, 3005, "today game is not exist");

    private final boolean isSuccess;
    private final int code;
    private final String message;


}
