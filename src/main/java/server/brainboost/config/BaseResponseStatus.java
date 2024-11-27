package server.brainboost.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    //요청 성공
    Success(true, 200, "ok");

    private final boolean isSuccess;
    private final int code;
    private final String message;


}
