package server.brainboost.exception.handler;

import server.brainboost.code.BaseErrorCode;
import server.brainboost.exception.GeneralException;

public class AuthenticationHandler extends GeneralException {

    public AuthenticationHandler(BaseErrorCode errorCode) {super(errorCode);}
}
