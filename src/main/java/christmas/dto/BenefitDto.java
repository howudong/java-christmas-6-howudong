package christmas.dto;

import christmas.domain.BadgeType;
import christmas.domain.OrderProduct;

public final class BenefitDto extends OutputDto {
    private final OrderProduct rewardProduct;
    private final BadgeType badgeType;
    private final Long benefitPrice;

    public BenefitDto(OrderProduct rewardProduct, BadgeType badgeType, Long benefitPrice) {
        this.rewardProduct = rewardProduct;
        this.badgeType = badgeType;
        this.benefitPrice = benefitPrice;
    }

    public OrderProduct rewardProduct() {
        return rewardProduct;
    }

    public BadgeType badgeType() {
        return badgeType;
    }

    public Long getBenefitPrice() {
        return benefitPrice;
    }
}
