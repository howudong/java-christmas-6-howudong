package christmas.domain.discounts;

import christmas.domain.Orders;
import christmas.domain.vo.MenuType;

final class WeekdayDiscountStrategy implements DiscountStrategy {
    private static final long WEEKDAY_DISCOUNT_PRICE = 2023L;

    @Override
    public Long discount(Orders orders) {
        boolean isWeekend = orders.isWeekend();
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
