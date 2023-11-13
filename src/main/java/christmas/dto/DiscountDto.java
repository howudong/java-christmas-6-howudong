package christmas.dto;

import java.util.Map;

public final class DiscountDto extends OutputDto {
    private final Map<String, Long> discounts;
    private final Long totalDiscountPrice;

    public DiscountDto(Map<String, Long> discounts, Long totalDiscountPrice) {
        this.discounts = discounts;
        this.totalDiscountPrice = totalDiscountPrice;
    }

    public Map<String, Long> getDiscounts() {
        return discounts;
    }
    
    public Long getTotalDiscountPrice() {
        return totalDiscountPrice;
    }
}
