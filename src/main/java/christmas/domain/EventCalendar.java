package christmas.domain;

import java.time.LocalDate;
import java.util.List;

public final class EventCalendar {
    private static final List<Integer> SPECIAL = List.of(3, 10, 17, 24, 25, 31);
    private static final int EVENT_YEAR = 2023;
    private static final int EVENT_MONTH = 12;

    public Boolean isSpecialDay(int day) {
        return SPECIAL.contains(day);
    }

    public Boolean isWeekend(int day) {
        LocalDate date = LocalDate.of(EVENT_YEAR, EVENT_MONTH, day);
        int weekIndex = date.getDayOfWeek().getValue();
        return weekIndex == 5 || weekIndex == 6;
    }
}
