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
    FAILED_LOGIN(false, 2000, "로그인에 실패했습니다"),
    NO_VALID_LOGINDTO(false, 2010, "LoginDTO의 형식에 맞게 로그인 해주세요"),
    INVALID_TOKEN(false, 2015, "토큰이 올바르지 않습니다"),
    EXPIRED_ACCESS_TOKEN(false, 2020, "access 토큰의 만료시간이 지났습니다"),
    EXPIRED_REFRESH_TOKEN(false, 2021, "refresh 토큰의 만료시간이 지났습니다"),
    UNEXPECTED_GENDER(false, 2500, "성별이 올바르지 않습니다"),
    REQUIRED_LOGIN(false, 2100, "로그인이 필요한 서비스 입니다"),
    USER_NO_EXIST(false, 2200, "존재하지 않는 유저입니다"),

    //game 오류 -> 3000번
    GAME_NO_EXIST(false, 3000, "game is not exist"),
    TODAY_GAME_NO_EXIST(false, 3005, "today game is not exist"),

    GAME_TYPE_NO_EXIST(false, 3010, "game type is not exist"),

    //medical 오류 -> 4000번
    MEDICAL_CHECKLIST_NO_EXIST(false, 4000, "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    MEDICAL_CHECKLIST_ALREADY_EXIST(false, 4001, "기본 건강 정보를 이미 작성하셨습니다"),


    //global 오류 -> 5000번
    DATA_CONFLICT(false, 5000, "데이터 충돌로 업데이트 실패"),
    DATABASE_ERROR(false, 5100, "데이터베이스 오류"),
    PARAMETER_ERROR(false, 5200, "파라미터 오류입니다"),
    HEADER_ERROR(false, 5300, "요청의 헤더 부분에 에러가 발생했습니다");


    private final boolean isSuccess;
    private final int code;
    private final String message;


}
