package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.EventCalendar;
import christmas.domain.Orders;

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
        if (orders.getOriginalPrice() < DISCOUNT_MIN_PRICE) {
            return Collections.emptyMap();
        }

        Map<String, Long> discounts = getNotZeroDiscountResults();
        sumAllDiscount(discounts);
        return discounts;
    }

    private Map<String, Long> getNotZeroDiscountResults() {
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

    private Long sumAllDiscount(Map<String, Long> discounts) {
        return discounts.values()
                .stream()
                .reduce(Long::sum)
                .orElse(0L);
    }
}
