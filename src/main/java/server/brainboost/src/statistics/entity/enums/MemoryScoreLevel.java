package server.brainboost.src.statistics.entity.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

@Slf4j
public enum MemoryScoreLevel {

    LOW   (0,  33, "기억력 점수 낮음"),
    MID   (34, 66, "기억력 점수 중간"),
    HIGH  (67,100, "기억력 점수 높음");

    private final int min, max;
    private final String message;

    MemoryScoreLevel(int min, int max, String message) {
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

    public static MemoryScoreLevel of(int score) {
        for (MemoryScoreLevel level : values()) {
            if (level.inRange(score)) return level;
        }
        log.error("기억력 영역의 총 합계 점수가 100점을 초과하거나 0점 아래로 내려갔습니다.");
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}
