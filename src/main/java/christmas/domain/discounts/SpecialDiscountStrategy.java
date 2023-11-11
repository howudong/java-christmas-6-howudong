package christmas.domain.discounts;

import christmas.domain.Calendar;
import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;

final class SpecialDiscountStrategy implements DiscountStrategy {

    private static final long SPECIAL_DISCOUNT_PRICE = 1000L;

    @Override
    public Long discount(Orders orders) {
        Calendar orderMonth = orders.orderMonth();
        int orderDay = orders.orderDay();

        return getDiscountPrice(orderMonth, orderDay);
    }

    private Long getDiscountPrice(Calendar orderMonth, int orderDay) {
        if (orderMonth.equals(Calendar.DECEMBER) && orderMonth.isStarDay(orderDay)) {
            return SPECIAL_DISCOUNT_PRICE;
        }
        return 0L;
    }
}
