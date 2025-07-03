package server.brainboost.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

@Slf4j
public enum TotalScoreLevel {
    LOW   (0,  33, "총합 점수 낮음"),
    MID   (34, 66, "총합 점수 중간"),
    HIGH  (67,100, "총합 점수 높음");

    private final int min, max;
    private final String message;

    TotalScoreLevel(int min, int max, String message) {
        this.min = min;
        this.max = max;
        this.message = message;
    }

    public boolean inRange(int score) {
        return score >= min && score <= max;
    }

    public String getMessage() {
        return message;
    }

    public static TotalScoreLevel of(int score) {
        for (TotalScoreLevel level : values()) {
            if (level.inRange(score)) return level;
        }
        log.error("통계 영역의 총 합계 점수가 100점을 초과하거나 0점 아래로 내려갔습니다.");
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}
