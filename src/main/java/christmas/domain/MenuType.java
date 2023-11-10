package christmas.domain;

import java.util.List;

import static christmas.domain.Products.*;

public enum MenuType {
    APPETIZER(List.of(MUSHROOM_SOUP, TAPAS, CAESAR_SALAD)),
    MAIN(List.of(T_BONE_STAKE, BBQ_LIBS, SEA_FOOD_PASTA, CHRISTMAS_PASTA)),
    BEVERAGE(List.of(ZERO_COKE, RED_WINE, CHAMPAGNE)),
    DESERT(List.of(ICECREAM, CHOCOLATE_CAKE));

    private final List<Products> products;

    MenuType(List<Products> products) {
        this.products = products;
    }
}
