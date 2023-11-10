package christmas.domain;

import java.util.Arrays;
import java.util.List;

import static christmas.domain.Product.*;

public enum MenuType {
    APPETIZER(List.of(MUSHROOM_SOUP, TAPAS, CAESAR_SALAD)),
    MAIN(List.of(T_BONE_STAKE, BBQ_LIBS, SEA_FOOD_PASTA, CHRISTMAS_PASTA)),
    DRINK(List.of(ZERO_COKE, RED_WINE, CHAMPAGNE)),
    DESERT(List.of(ICECREAM, CHOCOLATE_CAKE));

    private final List<Product> products;

    MenuType(List<Product> products) {
        this.products = products;
    }

    static MenuType getMenuTypeByName(String productName) {
        return Arrays.stream(MenuType.values())
                .filter(e -> e.name().equals(productName))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
