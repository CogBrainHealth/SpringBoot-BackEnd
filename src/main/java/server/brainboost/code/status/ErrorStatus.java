package server.brainboost.code.status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import server.brainboost.code.BaseErrorCode;
import server.brainboost.code.ErrorReasonDTO;
import server.brainboost.code.ReasonDTO;


@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 인증 오류
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4011",  "토큰이 유효하지 않습니다."),
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH4012",  "액세스 토큰이 만료되었습니다."),
    AUTHENTICATION_HEADER_ERROR(HttpStatus.UNAUTHORIZED, "AUTH4013",  "Authorization 헤더가 올바르지 않습니다."),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH4014", "아이디 또는 비밀번호가 일치하지 않습니다. 다시 로그인 해주세요."),

    //user 오류 -> 2000번
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "USER4004", "refresh 토큰의 만료시간이 지났습니다"),
    UNEXPECTED_GENDER(HttpStatus.BAD_REQUEST, "USER4005", "성별이 올바르지 않습니다"),
    USER_NO_EXIST(HttpStatus.NOT_FOUND, "USER4007", "존재하지 않는 유저입니다"),
    USER_NO_PREMIUM(HttpStatus.NOT_FOUND, "USER4008", "프리미엄 유저가 아닙니다"),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "USER4009", "해당 유저가 이미 존재합니다"),

    //statistic 오류
    GAMEPLAY_NOT_YET(HttpStatus.NOT_FOUND, "STATISTIC4001", "아직 게임을 하지 않아 점수 통계가 없습니다"),

    //game 오류 -> 3000번
    GAME_NO_EXIST(HttpStatus.NOT_FOUND, "GAME4001", "해당 게임이 존재하지 않습니다"),
    TODAY_GAME_NO_EXIST(HttpStatus.NOT_FOUND, "GAME4002", "오늘의 게임이 존재하지 않습니다"),

    GAME_TYPE_NO_EXIST(HttpStatus.NOT_FOUND, "GAME4003", "해당 유형의 게임이 존재하지 않습니다"),

    //medical 오류 -> 4000번
    MEDICAL_CHECKLIST_NO_EXIST(HttpStatus.NOT_FOUND, "MEDICAL4001", "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    MEDICAL_CHECKLIST_ALREADY_EXIST(HttpStatus.UNPROCESSABLE_ENTITY, "MEDICAL4002", "기본 건강 정보를 이미 작성하셨습니다"),
    PREMIUM_MEDICAL_CHECKLIST_NO_EXIST(HttpStatus.NOT_FOUND, "MEDICAL4003", "프리미엄 건강 정보를 아직 작성하지 않으셨습니다"),
    PREMIUM_MEDICAL_CHECKLIST_ALREADY_EXIST(HttpStatus.UNPROCESSABLE_ENTITY, "MEDICAL4004", "프리미엄 건강 정보를 이미 작성하셨습니다"),

    NUTRIENT_NOT_EXIST(HttpStatus.NOT_FOUND, "NUTRIENT4005", "영양소 정보가 존재하지 않습니다"),

    //global 오류 -> 5000번
    DATA_CONFLICT(HttpStatus.CONFLICT, "COMMON409", "데이터 충돌로 업데이트 실패"),
    DATABASE_ERROR(HttpStatus.CONFLICT, "COMMON409", "데이터베이스 오류"),
    PARAMETER_ERROR(HttpStatus.BAD_REQUEST, "COMMON400", "파라미터 오류입니다");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
