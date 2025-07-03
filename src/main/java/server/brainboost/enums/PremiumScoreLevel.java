package server.brainboost.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

import java.util.List;

@Slf4j
public enum PremiumScoreLevel {

    LOW   (0,  17, "전반적 인지 기능 저하 신호","이 범위에서는 전반적인 인지 처리 능력에서 변화 가능성이 높아, 일상생활에서 다음과 같은 어려움이 나타날 수 있습니다:" ,List.of("가족이나 익숙한 사람의 이름이 쉽게 떠오르지 않음", "날짜, 시간, 장소 등에 대한 혼동", "반복적인 질문, 같은 말을 여러 번 하는 경향", "일상적인 대화나 의사 표현의 어려움", "감정 조절의 어려움 또는 무기력감 증가")),
    MID   (18, 23, "경도 인지 변화 가능성", "이 점수 범위에서는 일부 인지 기능에서 변화가 감지될 수 있으며, 다음과 같은 증상을 경험하실 수 있습니다:" ,List.of("최근의 일이나 대화 내용을 바로 떠올리기 어려움", "일정, 약속, 업무 등에서 실수가 증가", "집중이 잘 되지 않거나 쉽게 피로해짐", "단어 선택이나 말의 흐름이 자연스럽지 않게 느껴짐", "기분 변화나 감정 기복이 잦아지는 경향")),
    HIGH  (24, 30, "인지 손상 없음","현재 인지 기능에서 특별한 변화 신호는 관찰되지 않았습니다.뇌 건강 유지를 위해 다음과 같은 활동을 추천드립니다:" ,List.of("규칙적인 운동", "충분한 수면과 일정한 수면 패턴 유지 ", "일상 속 대화와 사회적 교류", "퍼즐 맞추기, 단어 퀴즈, 계산 게임 등 인지 자극 활동", "균형 잡힌 식사와 영양분 섭취"));

    private final int min, max;
    private final String evaluation;
    private final String comment;
    private final List<String> feedbackList;

    PremiumScoreLevel(int min, int max, String evaluation, String comment, List<String> feedbackList) {
        this.min = min;
        this.max = max;
        this.evaluation = evaluation;
        this.comment = comment;
        this.feedbackList = feedbackList;
    }

    public boolean inRange(int score) {
        return score >= min && score <= max;
    }

    public String getEvaluation() {
        return evaluation;
    }
    public String getComment() {return comment;}
    public List<String> getFeedbackList() {return feedbackList;}

    public static PremiumScoreLevel of(int score) {
        for (PremiumScoreLevel level : values()) {
            if (level.inRange(score)) return level;
        }
        log.error("프리미엄 건강 테스트 점수의 총 합계 점수가 30점을 초과하거나 0점 아래로 내려갔습니다.");
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }

}
