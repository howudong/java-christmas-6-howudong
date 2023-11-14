package christmas.domain.vo;

import java.util.Arrays;

public enum Product {
    MUSHROOM_SOUP("양송이수프", 6_000L),
    TAPAS("타파스", 5_500L),
    CAESAR_SALAD("시저샐러드", 8_000L),
    T_BONE_STAKE("티본스테이크", 55_000L),
    BBQ_LIBS("바비큐립", 54_000L),
    SEA_FOOD_PASTA("해산물파스타", 35_000L),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000L),
    CHOCOLATE_CAKE("초코케이크", 15_000L),
    ICE_CREAM("아이스크림", 5_000L),
    ZERO_COKE("제로콜라", 3_000L),
    RED_WINE("레드와인", 60_000L),
    CHAMPAGNE("샴페인", 25_000L);

    private final String name;
    private final Long price;

    Product(String name, Long price) {
        this.name = name;
        this.price = price;
    }

    public static Product findSameProduct(String product) {
        return Arrays.stream(values())
                .filter(e -> e.name.equals(product))
                .findAny()
                .orElse(null);
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }
}
