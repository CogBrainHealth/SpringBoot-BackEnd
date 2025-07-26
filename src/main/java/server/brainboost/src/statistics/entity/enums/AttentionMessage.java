package server.brainboost.src.statistics.entity.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

@Slf4j
public enum AttentionMessage {

    TWENTIES(AgeGroup.TWENTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            🚀 **최고 수준의 주의력!**

            000님은 **20대 수준의 주의력**을 가지고 있어요!

            색깔과 단어가 일치하지 않는 상황에서도 빠르게 반응하고 정확한 판단을 내릴 수 있는 능력을 지니고 있네요.

            ✅ **이 수준을 유지하려면?**
            - 색깔과 단어가 섞인 정보를 빠르게 구별하는 연습을 꾸준히 해보세요.
            - 다양한 사고방식을 요구하는 퍼즐이나 게임을 통해 두뇌를 자극하는 것도 좋은 방법입니다!
            """),

    THIRTIES(AgeGroup.THIRTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            🎁 **우수한 주의력!**

            000님은 **30대 수준의 주의력**을 가지고 있어요!

            이해력을 효과적으로 조절하며, 주어진 정보 속에서 혼란을 최소화하는 능력이 뛰어납니다.

            ✅ **이 수준을 유지하려면?**
            - 집중력을 유지하는 습관을 들이고, 짧은 시간 동안 몰입할 수 있는 활동을 자주 해보세요.
            - 독서나 명상과 같은 집중력을 높이는 활동을 시도해보세요!
            """),

    FORTIES(AgeGroup.FORTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            👍 **균형 잡힌 주의력!**

            000님은 **40대 수준의 주의력**을 가지고 있어요!

            주의력을 활용하여 정확한 결정을 내릴 수 있으며, 복잡한 정보 속에서도 논리적인 판단을 할 수 있습니다.

            ✅ 다음과 같은 활동을 추천드려요!
            - 정보가 혼재된 상황에서도 논리적으로 판단하는 연습을 해보세요.
            - 새로운 문제를 해결하는 활동(퀴즈, 전략 게임 등)에 도전해보는 것도 좋습니다!
            """),

    FIFTIES(AgeGroup.FIFTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            🏃‍♂️ **훈련하면 더 좋아질 수 있어요!**

            000님은 **50대 수준의 주의력**을 가지고 있어요!

            주의를 분산시키는 요소가 있어도 적절히 조절하며 중요한 정보에 집중할 수 있는 능력을 갖추고 있습니다.

            ✅ 다음과 같은 활동을 추천드려요!
            - 복잡한 정보 속에서도 핵심을 파악하는 연습을 지속하세요.
            - 일상 속에서 멀티태스킹보다는 한 가지에 집중하는 습관을 들여보세요!
            """),

    SIXTIES(AgeGroup.SIXTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            🧠 **조금 더 자주 훈련하면 좋아질 수 있어요!**

            000님은 **60대 수준의 주의력**을 가지고 있어요!

            다소 속도가 느려질 수 있지만, 여전히 주어진 정보 속에서 중요한 부분을 파악하고 올바른 결정을 내릴 수 있는 능력이 있습니다.

            ✅ 다음과 같은 활동을 추천드려요!
            - 일상에서 색깔, 숫자, 단어를 활용한 게임을 꾸준히 해보세요.
            - 인지 유연성을 높이는 다양한 활동(새로운 취미 도전, 학습 등)에 참여해보세요!
            """),

    SEVENTIES(AgeGroup.SEVENTIES, """
            과일 속임수 게임을 통해 주의력을 검사했어요!

            🏋️ **조금 더 연습하면 주의력이 유지되고 향상될 거예요!**

            000님은 **70대 수준의 주의력**을 가지고 있어요!

            ✅ 다음과 같은 활동을 추천드려요!
            - 두뇌를 꾸준히 자극하는 활동(퍼즐, 독서, 글쓰기 등)을 실천해보세요.
            - 천천히, 하지만 정확하게 문제를 해결하는 연습을 해보는 것도 중요합니다!
            """);

    private final AgeGroup ageGroup;
    private final String message;

    AttentionMessage(AgeGroup ageGroup, String message) {
        this.ageGroup = ageGroup;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static AttentionMessage fromAgeGroup(AgeGroup group) {
        for (AttentionMessage level : values()) {
            if (level.ageGroup == group) return level;
        }
        log.error("알 수 없는 AgeGroup 값입니다: {}", group);
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}

