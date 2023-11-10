package christmas.domain.calendar;

import java.util.List;

public enum Calendar {
    DECEMBER(31, List.of(3, 10, 17, 24, 25, 31));
    private final int lastDay;
    private final List<Integer> discountDay;

    Calendar(int lastDay, List<Integer> discountDay) {
        this.lastDay = lastDay;
        this.discountDay = discountDay;
    }

    public boolean isStarDay(int day) {
        return discountDay.contains(day);
    }

    public int getLastDay() {
        return lastDay;
    }
}
