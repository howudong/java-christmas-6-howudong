package christmas.dto;

import christmas.domain.vo.BadgeType;

public final class BenefitDto extends OutputDto {
    private final ProductDto rewardProduct;
    private final BadgeType badgeType;
    private final Long benefitPrice;

    public BenefitDto(ProductDto rewardProduct, BadgeType badgeType, Long benefitPrice) {
        this.rewardProduct = rewardProduct;
        this.badgeType = badgeType;
        this.benefitPrice = benefitPrice;
    }

    public ProductDto rewardProduct() {
        return rewardProduct;
    }

    public BadgeType badgeType() {
        return badgeType;
    }

    public Long getBenefitPrice() {
        return benefitPrice;
    }
}
