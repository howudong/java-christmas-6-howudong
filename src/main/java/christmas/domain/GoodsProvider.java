package christmas.domain;

public final class GoodsProvider {
    private static final long TOTAL_GOAL_PRICE = 120_000L;
    private static final Product REWARD_PRODUCT = Product.CHAMPAGNE;

    public Product getBonusProduct(Long totalPrice) {
        if (totalPrice >= TOTAL_GOAL_PRICE) {
            return REWARD_PRODUCT;
        }
        return null;
    }

    public BadgeType getRewardBadge(Long benefitPrice) {
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
}
