package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;

final class SpecialDiscountStrategy implements DiscountStrategy {
    private static final long SPECIAL_DISCOUNT_PRICE = 1000L;

    private final EventCalendar eventCalendar;

    public SpecialDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    @Override
    public Long discount(Orders orders) {
        int orderDay = orders.getOrderDay();
        return getDiscountPrice(orderDay);
    }

    private Long getDiscountPrice(int orderDay) {
        if (Boolean.TRUE.equals(eventCalendar.isSpecialDay(orderDay))) {
            return SPECIAL_DISCOUNT_PRICE;
        }
        return 0L;
    }
}
