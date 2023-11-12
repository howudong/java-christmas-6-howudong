package christmas.dto;

import christmas.domain.BadgeType;
import christmas.domain.Product;

import java.util.Map;

public final class BenefitDto extends OutputDto {
    private final Map<String, Long> discounts;
    private final Product rewardProduct;
    private final BadgeType badgeType;

    public BenefitDto(Map<String, Long> discounts, Product rewardProduct, BadgeType badgeType) {
        this.discounts = discounts;
        this.rewardProduct = rewardProduct;
        this.badgeType = badgeType;
    }

    public Map<String, Long> discounts() {
        return discounts;
    }

    public Product rewardProduct() {
        return rewardProduct;
    }

    public BadgeType badgeType() {
        return badgeType;
    }
}
