package christmas.domain.discounts;

import christmas.domain.*;

import java.util.List;

final class WeekdayDiscountStrategy implements DiscountStrategy {
    private static final long WEEKDAY_DISCOUNT_PRICE = 2023L;
    private final EventCalendar eventCalendar;

    public WeekdayDiscountStrategy(EventCalendar eventCalendar) {
        this.eventCalendar = eventCalendar;
    }

    @Override
    public Long discount(Orders orders) {
        Boolean isWeekend = eventCalendar.isWeekend(orders.orderDay());
        if (Boolean.FALSE.equals(isWeekend)) {
            return getDiscountPrice(orders.orderProducts());
        }
        return 0L;
    }

    private Long getDiscountPrice(List<OrderProduct> orders) {
        int desertMenuSize = getTargetMenuQuantity(orders);
        return desertMenuSize * WEEKDAY_DISCOUNT_PRICE;
    }

    private int getTargetMenuQuantity(List<OrderProduct> orders) {
        return orders.stream()
                .filter(e -> MenuType.getMenuTypeByName(e.name()).equals(MenuType.DESERT))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
