package server.brainboost.src.statistics.entity.enums;

import lombok.extern.slf4j.Slf4j;
import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

@Slf4j
public enum SpatialPerceptionMessage {

    TWENTIES(AgeGroup.TWENTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        🚀 **최고 수준의 공간 지각 능력!**

        000님은 **20대 수준의 공간 지각 능력**을 가지고 있어요!

        주어진 공간에서 위치를 빠르게 파악하고, 다양한 형태나 구조를 정확히 이해할 수 있는 능력이 뛰어나네요.

        ✅ **이 수준을 유지하려면?**
        - 다양한 공간적 배열을 시각적으로 상상하며 훈련하세요.
        - 3D 퍼즐이나 레고 조립처럼 입체적인 활동을 통해 더욱 향상시킬 수 있어요!
        """),

    THIRTIES(AgeGroup.THIRTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        🎁 **우수한 공간 지각 능력!**

        000님은 **30대 수준의 공간 지각 능력**을 가지고 있어요!

        공간을 빠르게 이해하고 구조를 직관적으로 파악하는 능력이 뛰어나며, 여러 방향을 동시에 고려할 수 있어요.

        ✅ **이 능력을 더 키우려면?**
        - 다양한 방향으로 움직이며 공간을 회전시키는 연습을 해보세요.
        - 도형을 그려보며 공간적인 변화를 머릿속으로 상상해보는 것이 효과적입니다.
        """),

    FORTIES(AgeGroup.FORTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        👍 **균형 잡힌 공간 지각 능력!**

        000님은 **40대 수준의 공간 지각 능력**을 가지고 있어요!

        전체적인 공간을 잘 인지하고 지각하고 있어요.

        ✅ **좀 더 향상시키려면?**
        - 좀 더 복잡한 사물을 활용해보세요.
        - 공간을 인지할 때 '이 사물을 돌리면 어떤 모습이 될까?' 연상해보는 연습이 효과적입니다.
        """),

    FIFTIES(AgeGroup.FIFTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        🏃‍♂️ **훈련하면 더 좋아질 수 있어요!**

        000님은 **50대 수준의 공간 지각 능력**을 가지고 있어요!

        공간을 이해하는 데 시간이 조금 더 걸리거나, 경로와 위치를 기억하는 데 어려움이 있을 수 있어요.

        ✅ **더 나은 능력을 원한다면?**
        - 주변 환경을 이용해보세요!
        - 예: "사과를 거꾸로 뒤집으면 어떤 모양이 될까?"
        - 이런 방식으로 사물을 여러 방향으로 뒤집어 보면 공간 지각 능력이 향상됩니다!
        """),

    SIXTIES(AgeGroup.SIXTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        🧠 **조금 더 자주 훈련하면 좋아질 수 있어요!**

        000님은 **60대 수준의 공간 지각 능력**을 가지고 있어요!

        공간을 빠르게 이해하는 데 어려움이 있을 수 있으며, 방향을 기억하는 데 시간이 더 걸릴 수 있어요.

        ✅ **훈련을 위해?**
        - 우리에게 익숙한 사물을 돌려보며 연습해보세요!
        - 일상 속 사물(컵, 책, 휴대폰 등)을 다양한 방향으로 회전시키며 어떤 모습이 될지 상상해보세요.
        """),

    SEVENTIES(AgeGroup.SEVENTIES, """
        그림 돌리기 게임을 통해 공간 지각 능력을 검사했어요!

        🏋️ **조금 더 연습하면 공간 지각 능력이 향상될 거예요!**

        000님은 **70대 수준의 공간 지각 능력**을 가지고 있어요!

        공간을 인식하는 데 어려움이 있을 수 있지만, 꾸준한 훈련을 통해 점차 개선될 수 있어요.

        ✅ **훈련을 위해?**
        - 간단한 도형을 돌려보세요!
        - 예를 들어, 도형을 다양한 방식으로 회전해보며 형태의 변화를 상상해보는 연습이 효과적입니다!
        """);

    private final AgeGroup ageGroup;
    private final String message;

    SpatialPerceptionMessage(AgeGroup ageGroup, String message) {
        this.ageGroup = ageGroup;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public static SpatialPerceptionMessage fromAgeGroup(AgeGroup group) {
        for (SpatialPerceptionMessage level : values()) {
            if (level.ageGroup == group) return level;
        }
        log.error("알 수 없는 AgeGroup 값입니다: {}", group);
        throw new GeneralException(ErrorStatus._INTERNAL_SERVER_ERROR);
    }
}

