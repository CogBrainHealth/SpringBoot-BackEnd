package server.brainboost.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    //요청 성공
    Success(true, 200, "ok"),

    //user 오류 -> 2000번
    USER_NO_EXIST(false, 2000, "user is not exist");


    private final boolean isSuccess;
    private final int code;
    private final String message;


}
