package christmas.domain;

import java.util.Collections;
import java.util.HashMap;
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
        rewardProduct = getRewardProduct();
    }

    public Long getDiscountPrice() {
        return originTotalPrice - sumAllDiscount();
    }

    public Map<String, Long> getDiscounts() {
        Map<String, Long> copiedDiscounts = new HashMap<>(discounts);
        if (rewardProduct != null) {
            copiedDiscounts.put("증정 상품", rewardProduct.getPrice());
        }
        return Collections.unmodifiableMap(copiedDiscounts);
    }

    public Product getRewardProduct() {
        if (rewardProduct != null) {
            return rewardProduct;
        }

        if (originTotalPrice >= TOTAL_GOAL_PRICE) {
            rewardProduct = REWARD_PRODUCT;
            return REWARD_PRODUCT;
        }
        return null;
    }

    public BadgeType getRewardBadge() {
        Long benefitPrice = getBenefitPrice();
        return getBadgeType(benefitPrice);
    }

    public Long getBenefitPrice() {
        if (rewardProduct == null) {
            return sumAllDiscount();
        }
        return sumAllDiscount() + rewardProduct.getPrice();
    }

    private BadgeType getBadgeType(Long benefitPrice) {
        if (benefitPrice >= BadgeType.SANTA.getGoalPrice()) {
            return BadgeType.SANTA;
        }
        if (benefitPrice >= BadgeType.TREE.getGoalPrice()) {
            return BadgeType.TREE;
        }
        if (benefitPrice >= BadgeType.STAR.getGoalPrice()) {
            return BadgeType.STAR;
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
