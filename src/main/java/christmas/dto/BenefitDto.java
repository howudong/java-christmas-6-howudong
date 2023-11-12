package christmas.dto;

import christmas.domain.BadgeType;
import christmas.domain.Product;

import java.util.Map;

public record BenefitDto(Map<String, Long> discounts, Product rewardProduct, BadgeType badgeType) {
}
