package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DiscountCalculator {
    private static final long DISCOUNT_MIN_PRICE = 10000L;
    private final Orders orders;

    public DiscountCalculator(Orders orders) {
        this.orders = orders;
    }

    private final Map<String, DiscountStrategy> strategies = Map.ofEntries(
            Map.entry("크리스마스 디데이 할인", new XmasDiscountStrategy()),
            Map.entry("특별 할인", new SpecialDiscountStrategy(new EventCalendar())),
            Map.entry("평일 할인", new WeekdayDiscountStrategy(new EventCalendar())),
            Map.entry("주말 할인", new WeekendDiscountStrategy(new EventCalendar())));

    public Map<String, Long> getAvailableDiscounts() {
        if (orders.calculateTotalPrice() < DISCOUNT_MIN_PRICE) {
            return Collections.emptyMap();
        }
        return getNotZeroDiscountResults();
    }

    private Map<String, Long> getNotZeroDiscountResults() {
        Map<String, Long> result = new HashMap<>();
        strategies.forEach((key, value)
                -> hasDiscountPrice(orders, result, key, value));
        return result;
    }

    private void hasDiscountPrice(Orders orders, Map<String, Long> result, String key, DiscountStrategy value) {
        if (value.discount(orders) == 0L) {
            return;
        }
        result.put(key, value.discount(orders));
    }
}
