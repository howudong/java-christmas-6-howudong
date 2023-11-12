package christmas.domain;

import java.util.Map;

public final class Benefit {
    private static final long TOTAL_GOAL_PRICE = 120_000L;
    private static final Product REWARD_PRODUCT = Product.CHAMPAGNE;
    private final Long originTotalPrice;
    private final Map<String, Long> discounts;
    private Product rewardProduct;

    public Benefit(Long originTotalPrice, Map<String, Long> discounts) {
        this.originTotalPrice = originTotalPrice;
        this.discounts = discounts;
    }

    public Long getBenefitPrice() {
        return sumAllDiscount() + rewardProduct.getPrice();
    }

    public Product getRewardProduct() {
        if (rewardProduct != null) {
            return rewardProduct;
        }

        if (originTotalPrice >= TOTAL_GOAL_PRICE) {
            return REWARD_PRODUCT;
        }
        return null;
    }

    private Long sumAllDiscount() {
        return discounts.values()
                .stream()
                .reduce(Long::sum)
                .orElse(0L);
    }
}
