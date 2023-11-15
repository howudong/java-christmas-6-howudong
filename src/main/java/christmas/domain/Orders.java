package christmas.domain;

import christmas.domain.vo.MenuType;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static christmas.util.ErrorManager.*;
import static java.util.stream.Collectors.toSet;

public final class Orders {
    private static final int MAX_ORDER_SIZE = 20;
    private static final int FIRST_DAY = 1;
    private static final int LAST_DAY = 31;

    private final List<OrderProduct> orderProducts;
    private final int orderDay;

    public Orders(List<OrderProduct> orderProducts, int orderDay) {
        validateOrderProduct(orderProducts);
        validateDate(orderDay);
        this.orderProducts = orderProducts;
        this.orderDay = orderDay;
    }

    public int findAllTargetMenuType(MenuType menuType) {
        return orderProducts.stream()
                .map(e -> e.findSameMenuTypeQuantity(menuType))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public Long calculateTotalPrice() {
        return orderProducts.stream()
                .map(OrderProduct::sumOrderProduct)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public int getOrderDay() {
        return orderDay;
    }

    private void validateOrderProduct(List<OrderProduct> orderProducts) {
        validateEmpty(orderProducts);
        validateSameProduct(orderProducts);
        validateOrderSize(orderProducts);
        validateOnlyDrink(orderProducts);
    }

    private void validateEmpty(List<OrderProduct> orderProducts) {
        if (orderProducts.isEmpty()) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }


    private void validateDate(int orderDay) {
        if (orderDay < FIRST_DAY || orderDay > LAST_DAY) {
            throw new IllegalArgumentException(getText(INVALID_DATE));
        }
    }

    private void validateSameProduct(List<OrderProduct> orderProducts) {
        int distinctSize = new HashSet<OrderProduct>(orderProducts).size();

        if (orderProducts.size() != distinctSize) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateOrderSize(List<OrderProduct> orderProducts) {
        int totalOrderSize = orderProducts.stream()
                .map(OrderProduct::getQuantity)
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
                .map(OrderProduct::findMenuType)
                .collect(toSet());
    }
}
