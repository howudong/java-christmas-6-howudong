package christmas.domain.vo;

import java.util.List;

import static christmas.util.ErrorManager.INVALID_DATE;
import static christmas.util.ErrorManager.getText;

public record EventCalendar(int orderDay) {
    public static final int EVENT_YEAR = 2023;
    public static final int EVENT_MONTH = 12;
    public static final List<Integer> SPECIAL = List.of(3, 10, 17, 24, 25, 31);
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;

    public EventCalendar {
        validateDate(orderDay);
    }

    private void validateDate(int orderDay) {
        if (orderDay < FIRST_DAY || orderDay > LAST_DAY) {
            throw new IllegalArgumentException(getText(INVALID_DATE));
        }
    }
}
