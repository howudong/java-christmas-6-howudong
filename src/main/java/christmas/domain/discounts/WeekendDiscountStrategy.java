package christmas.domain.discounts;

import christmas.domain.Orders;
import christmas.domain.vo.MenuType;

final class WeekendDiscountStrategy implements DiscountStrategy {
    private static final long WEEKEND_DISCOUNT_PRICE = 2023L;


    @Override
    public Long discount(Orders orders) {
        boolean isWeekend = orders.isWeekend();

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
