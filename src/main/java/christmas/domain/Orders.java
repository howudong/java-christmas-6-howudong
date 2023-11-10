package christmas.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static christmas.util.ErrorHandler.INVALID_ORDER;
import static christmas.util.ErrorHandler.getText;

public final class Orders {
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        orders.forEach(this::validateExistProduct);
        validateSameName(orders);
        validateEmpty(orders);
        this.orders = orders;
    }

    private void validateEmpty(List<Order> orders) {
        if (orders.isEmpty()) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateSameName(List<Order> orders) {
        int distinctSize = new HashSet<Order>(orders).size();

        if (orders.size() != distinctSize) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateExistProduct(Order order) {
        boolean isMatch = Arrays.stream(Product.values())
                .anyMatch(e -> e.getName().equals(order.name()));

        if (!isMatch) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }
}
