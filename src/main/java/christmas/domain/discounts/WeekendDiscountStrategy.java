package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.MenuType;

final class WeekendDiscountStrategy implements DiscountStrategy {
    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;

    private final EventCalendar eventCalendar;

    public WeekendDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    @Override
    public Long discount(Orders orders) {
        boolean isWeekend = eventCalendar.isWeekend(orders.getOrderDay());

        if (isWeekend) {
            return getDiscountPrice(orders);
        }
        return 0L;
    }

    private Long getDiscountPrice(Orders orders) {
        int mainMenuSize = orders.findAllTargetMenuType(MenuType.MAIN);
        return mainMenuSize * WEEKEND_DISCOUNT_PRICE;
    }
}
