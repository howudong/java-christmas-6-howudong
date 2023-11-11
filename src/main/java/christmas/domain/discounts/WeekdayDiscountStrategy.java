package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.MenuType;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;

import java.util.List;

final class WeekdayDiscountStrategy implements DiscountStrategy {
    private static final long WEEKDAY_DISCOUNT_PRICE = 2023L;
    private static final int WEEK_CYCLE = 7;

    @Override
    public Long discount(Orders orders) {
        if (isWeekDay(orders)) {
            return getDiscountPrice(orders.orderProducts());
        }
        return 0L;
    }

    private boolean isWeekDay(Orders orders) {
        List<Integer> firstWeekendsDays = orders.orderMonth().getFirstWeekendDays();
        int orderDay = orders.orderDay();

        return !firstWeekendsDays.contains(orderDay % WEEK_CYCLE);
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
