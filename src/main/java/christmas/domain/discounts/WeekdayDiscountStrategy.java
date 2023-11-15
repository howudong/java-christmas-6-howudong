package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.MenuType;

final class WeekdayDiscountStrategy implements DiscountStrategy {
    private static final long WEEKDAY_DISCOUNT_PRICE = 2023L;

    private final EventCalendar eventCalendar;

    public WeekdayDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    @Override
    public Long discount(Orders orders) {
        boolean isWeekend = eventCalendar.isWeekend(orders.getOrderDay());
        if (!isWeekend) {
            return calculateDiscountPrice(orders);
        }
        return 0L;
    }

    private Long calculateDiscountPrice(Orders orders) {
        int desertMenuSize = orders.findAllTargetMenuType(MenuType.DESERT);
        return desertMenuSize * WEEKDAY_DISCOUNT_PRICE;
    }
}
