package christmas.domain;

public final class RewardProvider {
    private static final long TOTAL_GOAL_PRICE = 120_000L;
    private static final Product REWARD_PRODUCT = Product.CHAMPAGNE;

    public Product getBonusProduct(Long totalPrice) {
        if (totalPrice >= TOTAL_GOAL_PRICE) {
            return REWARD_PRODUCT;
        }
        return null;
    }
}
