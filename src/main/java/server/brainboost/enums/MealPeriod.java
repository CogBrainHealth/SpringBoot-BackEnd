package server.brainboost.enums;

import java.util.concurrent.ThreadLocalRandom;

public enum MealPeriod {
    아침,
    점심,
    저녁,
    간식;

    private static final MealPeriod[] VALUES = values();
    private static final int SIZE = VALUES.length;

    /** 랜덤한 MealPeriod 하나를 반환합니다. */
    public static MealPeriod randomPeriod() {
        int idx = ThreadLocalRandom.current().nextInt(SIZE);
        return VALUES[idx];
    }

}
