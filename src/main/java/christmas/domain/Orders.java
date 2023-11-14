package christmas.domain;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static christmas.util.ErrorHandler.*;

public record Orders(List<OrderProduct> orderProducts, int orderDay) {
    private static final int MAX_ORDER_SIZE = 20;
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;

    public Orders {
        validateOrderProduct(orderProducts);
        getValidateDate(orderDay);
    }

    public Long getOriginalPrice() {
        return orderProducts.stream()
                .map(e -> Product.getPriceByName(e.name()) * e.quantity())
                .reduce(Long::sum)
                .orElse(0L);
    }

    private void getValidateDate(int orderDay) {
        if (orderDay < FIRST_DAY || orderDay > LAST_DAY) {
            throw new IllegalArgumentException(getText(INVALID_DATE));
        }
    }

    private void validateOrderProduct(List<OrderProduct> orderProducts) {
        validateEmpty(orderProducts);
        orderProducts.forEach(this::validateExistProduct);
        validateSameName(orderProducts);
        validateEmptyQuantity(orderProducts);
        validateOrderSize(orderProducts);
        validateOnlyDrink(orderProducts);
    }

    private void validateEmpty(List<OrderProduct> orderProducts) {
        if (orderProducts.isEmpty()) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateExistProduct(OrderProduct orderProduct) {
        boolean isMatch = Arrays.stream(Product.values())
                .anyMatch(e -> e.getName().equals(orderProduct.name()));

        if (!isMatch) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateSameName(List<OrderProduct> orderProducts) {
        int distinctSize = orderProducts.stream()
                .map(OrderProduct::name)
                .collect(Collectors.toSet())
                .size();

        if (orderProducts.size() != distinctSize) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateEmptyQuantity(List<OrderProduct> orderProducts) {
        boolean containEmpty = orderProducts.stream().anyMatch(e -> e.quantity() == 0);

        if (containEmpty) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateOrderSize(List<OrderProduct> orderProducts) {
        int totalOrderSize = orderProducts.stream()
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        if (totalOrderSize > MAX_ORDER_SIZE) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateOnlyDrink(List<OrderProduct> orderProducts) {
        Set<MenuType> orderMenuTypes = convertMenuTypes(orderProducts);
        boolean isOnlyDrink = orderMenuTypes.stream()
                .allMatch(e -> e.equals(MenuType.DRINK));

        if (isOnlyDrink) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private Set<MenuType> convertMenuTypes(List<OrderProduct> orderProducts) {
        return orderProducts.stream()
                .map(e -> MenuType.getMenuTypeByName(e.name()))
                .collect(Collectors.toSet());
    }
}
