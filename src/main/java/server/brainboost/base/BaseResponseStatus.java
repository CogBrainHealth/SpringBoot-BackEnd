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
    UNEXPECTED_GENDER(false, 2020, "성별이 올바르지 않습니다"),
    REQUIRED_LOGIN(false, 2100, "로그인이 필요한 서비스 입니다"),

    //game 오류 -> 3000번
    GAME_NO_EXIST(false, 3000, "game is not exist"),
    TODAY_GAME_NO_EXIST(false, 3005, "today game is not exist"),

    GAME_TYPE_NO_EXIST(false, 3010, "game type is not exist"),

    //medical 오류 -> 4000번
    MEDICAL_CHECKLIST_NO_EXIST(false, 4000, "기본 건강 정보를 아직 작성하지 않으셨습니다"),
    MEDICAL_CHECKLIST_ALREADY_EXIST(false, 4001, "기본 건강 정보를 이미 작성하셨습니다"),


    //global 오류 -> 5000번
    DATA_CONFLICT(false, 5000, "데이터 충돌로 업데이트 실패"),
    DATABASE_ERROR(false, 5100, "데이터베이스 오류");


    private final boolean isSuccess;
    private final int code;
    private final String message;


}
