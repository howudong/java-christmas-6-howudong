package christmas.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static christmas.util.ErrorHandler.INVALID_ORDER;
import static christmas.util.ErrorHandler.getText;

public final class Orders {
    private static final int MAX_ORDER_SIZE = 20;
    private final List<Order> orders;

    public Orders(List<Order> orders) {
        validate(orders);
        this.orders = orders;
    }

    private void validate(List<Order> orders) {
        validateEmpty(orders);
        orders.forEach(this::validateExistProduct);
        validateSameName(orders);
        validateOrderSize(orders);
        validateOnlyDrink(orders);
    }

    private void validateEmpty(List<Order> orders) {
        if (orders.isEmpty()) {
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

    private void validateSameName(List<Order> orders) {
        int distinctSize = orders.stream()
                .map(Order::name)
                .collect(Collectors.toSet())
                .size();

        if (orders.size() != distinctSize) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateOrderSize(List<Order> orders) {
        if (orders.size() > MAX_ORDER_SIZE) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateOnlyDrink(List<Order> orders) {
        Set<MenuType> orderMenuTypes = convertMenuTypes(orders);

        boolean isOnlyDrink = orderMenuTypes.stream()
                .allMatch(e -> e.equals(MenuType.DRINK));

        if (isOnlyDrink) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private Set<MenuType> convertMenuTypes(List<Order> orders) {
        return orders.stream()
                .map(e -> MenuType.getMenuTypeByName(e.name()))
                .collect(Collectors.toSet());
    }
}
