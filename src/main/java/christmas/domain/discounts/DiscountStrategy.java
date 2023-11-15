package christmas.domain.discounts;

import christmas.domain.Orders;

public interface DiscountStrategy {
    Long discount(Orders orders);
}
