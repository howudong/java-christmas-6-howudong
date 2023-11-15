package christmas.domain;

import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.MenuType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static christmas.domain.vo.EventCalendar.*;
import static christmas.util.ErrorManager.INVALID_ORDER;
import static christmas.util.ErrorManager.getText;
import static java.util.stream.Collectors.toSet;

public final class Orders {
    private static final int MAX_ORDER_SIZE = 20;
    private final List<OrderProduct> orderProducts;
    private final EventCalendar calendar;

    public Orders(List<OrderProduct> orderProducts, EventCalendar calendar) {
        validateOrderProduct(orderProducts);
        this.orderProducts = orderProducts;
        this.calendar = calendar;
    }

    public int findAllTargetMenuType(MenuType menuType) {
        return orderProducts.stream()
                .map(e -> e.findSameMenuTypeQuantity(menuType))
                .reduce(Integer::sum)
                .orElse(0);
    }

    public boolean isSpecialDay() {
        return SPECIAL.contains(calendar.orderDay());
    }

    public boolean isWeekend() {
        LocalDate date = LocalDate.of(EVENT_YEAR, EVENT_MONTH, calendar.orderDay());
        DayOfWeek dayOfWeek = date.getDayOfWeek();

        return (dayOfWeek == DayOfWeek.FRIDAY || dayOfWeek == DayOfWeek.SATURDAY);
    }

    public Long calculateTotalPrice() {
        return orderProducts.stream()
                .map(OrderProduct::sumOrderProduct)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public int findOrderDay() {
        return calendar.orderDay();
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
