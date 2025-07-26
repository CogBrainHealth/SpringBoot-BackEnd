package server.brainboost.src.statistics.entity.enums;

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
}
