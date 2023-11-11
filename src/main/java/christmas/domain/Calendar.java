package christmas.domain;

import java.util.List;

public enum Calendar {
    DECEMBER(List.of(1, 2), 31, List.of(3, 10, 17, 24, 25, 31));
    private final List<Integer> firstWeekendDays;
    private final int lastDay;
    private final List<Integer> discountDay;

    Calendar(List<Integer> firstWeekendDays, int lastDay, List<Integer> discountDay) {
        this.firstWeekendDays = firstWeekendDays;
        this.lastDay = lastDay;
        this.discountDay = discountDay;
    }

    public List<Integer> getFirstWeekendDays() {
        return firstWeekendDays;
    }

    public boolean isStarDay(int day) {
        return discountDay.contains(day);
    }

    public int getLastDay() {
        return lastDay;
    }
}
