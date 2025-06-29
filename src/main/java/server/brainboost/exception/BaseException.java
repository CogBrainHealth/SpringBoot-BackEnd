package server.brainboost.exception;

import lombok.Getter;
import server.brainboost.base.BaseResponseStatus;

@Getter
public class BaseException extends RuntimeException{

    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status){
        super(status.getMessage());
        this.status = status;
    }
}
