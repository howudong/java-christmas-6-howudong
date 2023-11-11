package christmas.domain.discounts;

import christmas.domain.Calendar;
import christmas.domain.MenuType;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;

import java.util.List;

public final class WeeklyDiscountStrategy implements DiscountStrategy {

    private static final int WEEK_CYCLE = 7;
    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;
    private static final long WEEKDAY_DISCOUNT_PRICE = 2023L;

    @Override
    public Long discount(Orders orders) {
        if (!orders.orderMonth().equals(Calendar.DECEMBER)) {
            return 0L;
        }

        if (isWeekend(orders)) {
            return applyWeekendDiscount(orders.orderProducts());
        }
        return applyWeekdayDiscount(orders.orderProducts());
    }

    private boolean isWeekend(Orders orders) {
        List<Integer> firstWeekendsDays = orders.orderMonth().getFirstWeekendDays();
        int orderDay = orders.orderDay();

        return firstWeekendsDays.contains(orderDay % WEEK_CYCLE);
    }

    private Long applyWeekdayDiscount(List<OrderProduct> orders) {
        int desertMenuSize = getTargetMenuQuantity(orders, MenuType.DESERT);
        return desertMenuSize * WEEKDAY_DISCOUNT_PRICE;
    }

    private Long applyWeekendDiscount(List<OrderProduct> orders) {
        int mainMenuSize = getTargetMenuQuantity(orders, MenuType.MAIN);
        return mainMenuSize * WEEKEND_DISCOUNT_PRICE;
    }

    private int getTargetMenuQuantity(List<OrderProduct> orders, MenuType targetMenu) {
        return orders.stream()
                .filter(e -> MenuType.getMenuTypeByName(e.name()).equals(targetMenu))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
