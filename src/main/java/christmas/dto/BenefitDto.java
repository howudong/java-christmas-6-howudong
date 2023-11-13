package christmas.dto;

import christmas.domain.BadgeType;
import christmas.domain.Product;

public final class BenefitDto extends OutputDto {
    private final Product rewardProduct;
    private final BadgeType badgeType;
    private final Long benefitPrice;

    public BenefitDto(Product rewardProduct, BadgeType badgeType, Long benefitPrice) {
        this.rewardProduct = rewardProduct;
        this.badgeType = badgeType;
        this.benefitPrice = benefitPrice;
    }

    public Product rewardProduct() {
        return rewardProduct;
    }

    public BadgeType badgeType() {
        return badgeType;
    }

    public Long getBenefitPrice() {
        return benefitPrice;
    }
}
