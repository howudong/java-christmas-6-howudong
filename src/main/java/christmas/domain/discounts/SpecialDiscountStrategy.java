package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.EventCalendar;
import christmas.domain.Orders;

final class SpecialDiscountStrategy implements DiscountStrategy {
    private final EventCalendar eventCalendar;

    public SpecialDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    private static final long SPECIAL_DISCOUNT_PRICE = 1000L;

    @Override
    public Long discount(Orders orders) {
        int orderDay = orders.orderDay();

        return getDiscountPrice(orderDay);
    }

    private Long getDiscountPrice(int orderDay) {
        if (Boolean.TRUE.equals(eventCalendar.isSpecialDay(orderDay))) {
            return SPECIAL_DISCOUNT_PRICE;
        }
        return 0L;
    }
}
