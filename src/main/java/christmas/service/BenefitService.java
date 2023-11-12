package christmas.service;

import christmas.domain.Benefit;
import christmas.domain.discounts.DiscountCalculator;
import christmas.dto.BenefitDto;

import java.util.Map;

public final class BenefitService {
    private final DiscountCalculator calculator;
    private final Long originalTotalPrice;

    public BenefitService(DiscountCalculator calculator, Long originalTotalPrice) {
        this.calculator = calculator;
        this.originalTotalPrice = originalTotalPrice;
    }

    public BenefitDto getBenefitDto() {
        Map<String, Long> discounts = calculator.getAvailableDiscounts();
        return configBenefit(discounts);
    }

    private BenefitDto configBenefit(Map<String, Long> discounts) {
        Benefit benefit = new Benefit(originalTotalPrice, discounts);
        benefit.getRewardBadge();
        benefit.getRewardProduct();
        return new BenefitDto(discounts, benefit.getRewardProduct(), benefit.getRewardBadge());
    }

}