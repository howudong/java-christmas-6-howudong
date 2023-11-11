package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.MenuType;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;

import java.util.List;

final class WeekendDiscountStrategy implements DiscountStrategy {

    private static final int WEEK_CYCLE = 7;
    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;

    @Override
    public Long discount(Orders orders) {
        if (isWeekend(orders)) {
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
                .filter(e -> MenuType.getMenuTypeByName(e.name()).equals(MenuType.MAIN))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);
    }

    private boolean isWeekend(Orders orders) {
        List<Integer> firstWeekendsDays = orders.orderMonth().getFirstWeekendDays();
        int orderDay = orders.orderDay();

        return firstWeekendsDays.contains(orderDay % WEEK_CYCLE);
    }
}
