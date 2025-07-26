package server.brainboost.src.statistics.entity.enums;

import server.brainboost.code.status.ErrorStatus;
import server.brainboost.exception.GeneralException;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public enum AgeGroup {

    TWENTIES("20대", 20, 29),
    THIRTIES("30대", 30, 39),
    FORTIES("40대", 40, 49),
    FIFTIES("50대", 50, 59),
    SIXTIES("60대", 60, 69),
    SEVENTIES("70대", 70, 79);

    private final String label;
    private final int minAge;
    private final int maxAge;

    AgeGroup(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public String getLabel() {
        return label;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public double getCenterAge() {
        return (minAge + maxAge) / 2.0;
    }

    public static AgeGroup fromGroupList(List<AgeGroup> groups) {
        if (groups == null || groups.isEmpty()) {
            throw new GeneralException(ErrorStatus.GAMEPLAY_NOT_YET);
        }

        // null 제외한 유효한 AgeGroup만 필터링
        List<AgeGroup> validGroups = groups.stream()
                .filter(Objects::nonNull)
                .toList();

        if (validGroups.isEmpty()) {
            throw new GeneralException(ErrorStatus.PARAMETER_ERROR);
        }

        double averageCenterAge = validGroups.stream()
                .mapToDouble(AgeGroup::getCenterAge)
                .average()
                .orElseThrow();

        return Arrays.stream(values())
                .min(Comparator.comparingDouble(group -> Math.abs(group.getCenterAge() - averageCenterAge)))
                .orElseThrow();
    }
}

