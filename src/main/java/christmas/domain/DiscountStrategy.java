package christmas.domain;

public interface DiscountStrategy {
    Long discount(Orders orders);
}
