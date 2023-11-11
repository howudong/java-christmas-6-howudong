package christmas.domain.discounts;

import christmas.domain.Calendar;
import christmas.domain.DiscountStrategy;
import christmas.domain.Orders;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DiscountAdapter {
    private static final long DISCOUNT_MIN_PRICE = 10000L;
    private final Map<String, DiscountStrategy> strategies = Map.ofEntries(
            Map.entry("크리스마스 디데이 할인", new XmasDiscountStrategy()),
            Map.entry("특별 할인", new SpecialDiscountStrategy()),
            Map.entry("평일 할인", new WeekdayDiscountStrategy()),
            Map.entry("주말 할인", new WeekendDiscountStrategy()));

    public Map<String, Long> getAvailableDiscounts(Orders orders) {
        if (!orders.orderMonth().equals(Calendar.DECEMBER)
                || orders.getOriginalPrice() < DISCOUNT_MIN_PRICE) {
            return Collections.emptyMap();
        }
        return getNotZeroDiscountResults(orders);
    }

    private Map<String, Long> getNotZeroDiscountResults(Orders orders) {
        Map<String, Long> result = new HashMap<>();
        strategies.forEach((key, value)
                -> hasDiscountPrice(orders, result, key, value));
        return result;
    }

    private void hasDiscountPrice(Orders orders, Map<String, Long> result,
                                  String key, DiscountStrategy value) {
        if (value.discount(orders) != 0L) {
            result.put(key, value.discount(orders));
        }
    }
}
