package christmas.domain.discounts;

import christmas.domain.Calendar;
import christmas.domain.Orders;

public final class XmasDiscountStrategy implements DiscountStrategy {

    private static final int DEAD_LINE = 25;
    private static final Long DEFAULT_DISCOUNT_PRICE = 1000L;
    private static final Long EXTRA_DISCOUNT_PER_DAY = 100L;

    @Override
    public Long discount(Orders orders) {
        int orderDay = orders.orderDay();
        Calendar orderMonth = orders.orderMonth();

        return getDiscountPrice(orderDay, orderMonth);
    }

    private Long getDiscountPrice(int orderDay, Calendar orderMonth) {
        if (orderDay > DEAD_LINE || !orderMonth.equals(Calendar.DECEMBER)) {
            return 0L;
        }
        Long extraDiscount = (orderDay - 1) * EXTRA_DISCOUNT_PER_DAY;
        return DEFAULT_DISCOUNT_PRICE + extraDiscount;
    }
}
