package christmas.dto;

public final class DiscountDto extends OutputDto {
    private final Long benefitPrice;
    private final Long totalDiscountPrice;

    public DiscountDto(Long benefitPrice, Long totalDiscountPrice) {
        this.benefitPrice = benefitPrice;
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public Long benefitPrice() {
        return benefitPrice;
    }

    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }
}
