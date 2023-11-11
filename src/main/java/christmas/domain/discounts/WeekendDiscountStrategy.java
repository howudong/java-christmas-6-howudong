package christmas.domain.discounts;

import christmas.domain.MenuType;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;

import java.util.List;

public final class WeekendDiscountStrategy implements DiscountStrategy {

    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;

    @Override
    public Long discount(Orders orders) {
        return getDiscountPrice(orders.orderProducts());
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
}
