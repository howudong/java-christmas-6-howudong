package christmas.domain.vo;

public enum BadgeType {
    STAR("별", 5_000L),
    TREE("트리", 10_000L),
    SANTA("산타", 20_000L);
    private final String badgeName;
    private final Long goalPrice;

    BadgeType(String badgeName, Long goalPrice) {
        this.badgeName = badgeName;
        this.goalPrice = goalPrice;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public Long getGoalPrice() {
        return goalPrice;
    }
}
