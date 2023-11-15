package christmas.service;

import christmas.domain.Benefit;
import christmas.domain.discounts.DiscountCalculator;
import christmas.domain.vo.Product;
import christmas.dto.BenefitDto;
import christmas.dto.ProductDto;

import java.util.Map;
import java.util.Optional;

import static christmas.domain.vo.Product.CHAMPAGNE;

public final class BenefitService {
    private static final long TOTAL_GOAL_PRICE = 120_000L;
    private static final Product REWARD_PRODUCT = CHAMPAGNE;
    private static final int REWARD_QUANTITY = 1;

    private final DiscountCalculator calculator;
    private final Long originTotalPrice;

    public BenefitService(DiscountCalculator calculator, Long originTotalPrice) {
        this.calculator = calculator;
        this.originTotalPrice = originTotalPrice;
    }

    public BenefitDto createBenefitDto() {
        Map<String, Long> discounts = calculator.getAvailableDiscounts();
        return configBenefit(discounts);
    }

    private BenefitDto configBenefit(Map<String, Long> discounts) {
        Optional<ProductDto> rewardDto = Optional.ofNullable(createRewardProductDto());

        Benefit benefit = rewardDto.map(e -> new Benefit(discounts, e.toEntity()))
                .orElse(new Benefit(discounts, null));

        return new BenefitDto(
                rewardDto.orElse(null),
                benefit.getRewardBadge(),
                benefit.getBenefitPrice());
    }

    private ProductDto createRewardProductDto() {
        if (originTotalPrice >= TOTAL_GOAL_PRICE) {
            return new ProductDto(REWARD_PRODUCT.getName(), REWARD_QUANTITY);
        }
        return null;
    }
}
