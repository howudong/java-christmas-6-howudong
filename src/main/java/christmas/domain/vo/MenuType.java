package christmas.domain.vo;

import java.util.Arrays;
import java.util.List;

import static christmas.domain.vo.Product.*;

public enum MenuType {
    APPETIZER(List.of(MUSHROOM_SOUP, TAPAS, CAESAR_SALAD)),
    MAIN(List.of(T_BONE_STAKE, BBQ_LIBS, SEA_FOOD_PASTA, CHRISTMAS_PASTA)),
    DRINK(List.of(ZERO_COKE, RED_WINE, CHAMPAGNE)),
    DESERT(List.of(ICE_CREAM, CHOCOLATE_CAKE));

    private final List<Product> products;

    MenuType(List<Product> products) {
        this.products = products;
    }

    public static MenuType findMenuTypeProduct(Product product) {
        return Arrays.stream(MenuType.values())
                .filter(e -> e.findMatchedType(e.products, product.getName()))
                .findAny()
                .orElse(null);
    }

    private boolean findMatchedType(List<Product> products, String productName) {
        return products.stream()
                .anyMatch(e -> e.getName().equals(productName));
    }
}
