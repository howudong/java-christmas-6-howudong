package christmas.domain;

import christmas.domain.vo.BadgeType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class Benefit {
    private final Map<String, Long> discounts;
    private final OrderProduct rewardProduct;

    public Benefit(Map<String, Long> discounts, OrderProduct rewardProduct) {
        this.discounts = discounts;
        this.rewardProduct = rewardProduct;
    }

    public Map<String, Long> getDiscounts() {
        Map<String, Long> copiedDiscounts = new HashMap<>(discounts);
        if (rewardProduct != null) {
            copiedDiscounts.put("증정 상품", rewardProduct.sumOrderProduct());
        }
        return Collections.unmodifiableMap(copiedDiscounts);
    }

    public BadgeType getRewardBadge() {
        Long benefitPrice = getBenefitPrice();
        return getBadgeType(benefitPrice);
    }

    public Long getBenefitPrice() {
        if (rewardProduct == null) {
            return sumAllDiscount();
        }
        return sumAllDiscount() + rewardProduct.sumOrderProduct();
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
