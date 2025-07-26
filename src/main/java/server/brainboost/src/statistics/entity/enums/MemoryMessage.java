package server.brainboost.src.statistics.entity.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

@Slf4j
public enum MemoryMessage {

    TWENTIES(AgeGroup.TWENTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        🚀 **최고 수준의 기억력!** 

        000님은 **20대 수준의 기억력**을 가지고 있어요!

        한 번 본 경로를 거의 완벽하게 기억하고 재현할 수 있는 능력을 가지고 있네요.

        ✅ **이 수준을 유지하려면?**
        - 경로를 기억할 때 시각적 이미지(나무, 건물, 표지판 등)를 떠올려 보세요.
        - 이동한 경로를 나중에 종이에 그려보며 복습하는 것도 좋은 방법입니다!
        """),

    THIRTIES(AgeGroup.THIRTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        🎁 **우수한 기억력!** 

        000님은 **30대 수준의 기억력**을 가지고 있어요!

        경로를 빠르게 파악하고 기억하는 능력이 뛰어나며, 실수 없이 재현할 가능성이 높아요.

        ✅ **이 능력을 더 키우려면?**
        - 이동 경로를 머릿속으로 떠올리며 복습하는 습관을 들여보세요.
        - 도착 후 "어떤 길을 어떻게 왔는지" 떠올려보며 점검하는 연습이 효과적입니다.
        """),

    FORTIES(AgeGroup.FORTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        👍 **균형 잡힌 기억력!** 

        000님은 **40대 수준의 기억력**을 가지고 있어요.

        전체 경로를 한 번에 기억하기보다는, **주요 지점(랜드마크 중심)으로 기억하는 경향**이 있어요.

        ✅ **좀 더 향상시키려면?**
        - 이동하면서 "내가 지난 장소에서 어떤 특징이 있었는지" 떠올리는 습관을 길러보세요.
        - 길을 걸은 후, 일정 시간이 지난 뒤 머릿속으로 경로를 다시 떠올려보는 연습을 추천합니다.
        """),

    FIFTIES(AgeGroup.FIFTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        🏃‍♂️ **훈련하면 더 좋아질 수 있어요!** 

        000님은 **50대 수준의 기억력**을 가지고 있어요.

        길을 찾을 때 여러 번 생각해야 하거나, 중간에 일부 경로를 잊을 가능성이 있어요.

        ✅ **더 나은 기억력을 원한다면?**
        - 이동할 때 경로를 **하나의 이야기(스토리)** 로 만들어보세요!
        - 예: "큰 나무를 지나면 왼쪽에 있는 빨간 건물을 보고, 그다음 횡단보도를 건넌다."
        - 이처럼 스토리로 연결하면 기억이 더 오래 유지됩니다!
        """),

    SIXTIES(AgeGroup.SIXTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        🧠 **조금 더 자주 훈련하면 좋아질 수 있어요!** 

        000님은 **60대 수준의 기억력을 가지고 있어요.**

        경로를 기억하는 데 시간이 걸릴 수 있으며, 일부 정보가 빠르게 사라질 수 있어요.

        ✅ **훈련을 위해?**
        - 작은 구간별로 이동 후, 10초 동안 멈춰서 "내가 방금 어디를 지나왔는지" 떠올려보세요.
        - 종이에 경로를 직접 그려보는 것도 기억력 강화에 효과적입니다!
        """),

    SEVENTIES(AgeGroup.SEVENTIES, """
        길찾기 게임을 통해 기억력을 검사했어요!

        🏋️ **조금 더 연습하면 기억력이 향상될 거예요!** 

        000님은 **70대 수준의 기억력을 가지고 있어요.**

        경로를 기억하는 것이 다소 어려울 수 있지만, 반복 학습을 통해 충분히 좋아질 수 있어요.

        ✅ **훈련을 위해?**
        - 짧고 간단한 경로부터 시작해 기억하는 연습을 해보세요.
        - 예를 들어, 집에서 편의점까지의 길을 기억한 뒤, 거리를 점점 늘려가는 방식이 효과적입니다!
        """);

    private final AgeGroup ageGroup;
    private final String message;

    MemoryMessage(AgeGroup ageGroup, String message) {
        this.ageGroup = ageGroup;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static MemoryMessage fromAgeGroup(AgeGroup group) {
        for (MemoryMessage level : values()) {
            if (level.ageGroup == group) return level;
        }
        log.error("알 수 없는 AgeGroup 값입니다: {}", group);
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}

