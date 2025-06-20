package server.brainboost.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
@JsonPropertyOrder({"isSuccess", "code", "message", "result"})
public class BaseResponse<T> {

    @JsonProperty("isSuccess")
    private boolean isSuccess;
    int code;
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;


    public BaseResponse(T data){
        this.data = data;
        this.code = BaseResponseStatus.Success.getCode();
        this.isSuccess = BaseResponseStatus.Success.isSuccess();
        this.message = BaseResponseStatus.Success.getMessage();
    }

    public BaseResponse(BaseResponseStatus status){
        this.data = null;
        this.code = status.getCode();
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();

    }

    public BaseResponse(BaseResponseStatus status, String errorMessage){
        this.data = null;
        this.code = status.getCode();
        this.isSuccess = status.isSuccess();
        this.message = errorMessage;
    }

    public static <T> BaseResponse<T> onSuccess(T data){
        return new BaseResponse<>(true, 200, BaseResponseStatus.Success.getMessage(), data);
    }

    public static <T> BaseResponse<T> onFailure(int code, String message, T data){
        return new BaseResponse<>(false, code, message, data);
    }


}
