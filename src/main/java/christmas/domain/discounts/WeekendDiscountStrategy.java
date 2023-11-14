package christmas.domain.discounts;

import christmas.domain.*;

import java.util.List;

final class WeekendDiscountStrategy implements DiscountStrategy {
    private final EventCalendar eventCalendar;

    public WeekendDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;

    @Override
    public Long discount(Orders orders) {
        Boolean isWeekend = eventCalendar.isWeekend(orders.orderDay());
        if (Boolean.TRUE.equals(isWeekend)) {
            return getDiscountPrice(orders.orderProducts());
        }
        return 0L;
    }

    private Long getDiscountPrice(List<OrderProduct> orders) {
        int mainMenuSize = getTargetMenuQuantity(orders);
        return mainMenuSize * WEEKEND_DISCOUNT_PRICE;
    }

    private int getTargetMenuQuantity(List<OrderProduct> orders) {
        return orders.stream()
                .filter(e -> MenuType.findMenuTypeByName(e.name()).equals(MenuType.MAIN))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
