package christmas.dto;

public final class DiscountDto extends OutputDto {
    private final Long benefitPrice;

    public DiscountDto(Long benefitPrice) {
        this.benefitPrice = benefitPrice;
    }

    public Long benefitPrice() {
        return benefitPrice;
    }
}
