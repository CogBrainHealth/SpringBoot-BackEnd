package server.brainboost.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import server.brainboost.code.BaseErrorCode;
import server.brainboost.code.ErrorReasonDTO;

@Getter
@AllArgsConstructor
public class GeneralException extends RuntimeException {

  private BaseErrorCode code;

  public ErrorReasonDTO getErrorReason() {
    return this.code.getReason();
  }

  public ErrorReasonDTO getErrorReasonHttpStatus(){
    return this.code.getReasonHttpStatus();
  }
}
