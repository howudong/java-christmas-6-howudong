package christmas.service;

import christmas.domain.discounts.DiscountCalculator;
import christmas.dto.DiscountDto;

import java.util.Map;

public final class DiscountService {
    private final DiscountCalculator calculator;

    private final Long originalTotalPrice;

    public DiscountService(DiscountCalculator calculator, Long originalTotalPrice) {
        this.calculator = calculator;
        this.originalTotalPrice = originalTotalPrice;
    }

    public DiscountDto createDiscountDto() {
        Map<String, Long> discounts = calculator.getAvailableDiscounts();
        return new DiscountDto(discounts, originalTotalPrice - sumAllPrice(discounts));
    }

    private Long sumAllPrice(Map<String, Long> discounts) {
        return discounts.values().stream().reduce(Long::sum).orElse(0L);
    }
}
