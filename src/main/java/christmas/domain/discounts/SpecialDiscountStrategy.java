package christmas.domain.discounts;

import christmas.domain.Orders;

final class SpecialDiscountStrategy implements DiscountStrategy {
    private static final long SPECIAL_DISCOUNT_PRICE = 1000L;

    @Override
    public Long discount(Orders orders) {
        if (orders.isSpecialDay()) {
            return SPECIAL_DISCOUNT_PRICE;
        }
        return 0L;
    }
}
