package christmas.domain;

import christmas.util.ErrorHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.Product.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrdersTest {
    @Test
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void null_Order_예외() {
        assertThatThrownBy(() -> new Orders(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createAbsentNamedOrder")
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void 해당_메뉴_없음_예외(List<Order> orders) {
        assertThatThrownBy(() -> new Orders(orders))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createSameOrder")
    @DisplayName("같은 이름의 메뉴를 주문하면 예외가 발생한다.")
    void 메뉴_중복_예외(List<Order> param) {
        assertThatThrownBy(() -> new Orders(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOverSizeOrder")
    @DisplayName("주문 개수가 20개가 넘어가면 예외를 발생시킨다.")
    void 수량_초과_예외(List<Order> param) {
        assertThatThrownBy(() -> new Orders(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOnlyDrinkOrder")
    @DisplayName("DRINK만 주문하면 예외가 발생한다.")
    void 오직_마실것_예외(List<Order> param) {
        assertThatThrownBy(() -> new Orders(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createZeroQuantityOrder")
    @DisplayName("주문 개수가 0개인 것이 있다면 예외를 발생시킨다.")
    void 주문_개수_0개_예외(List<Order> param) {
        assertThatThrownBy(() -> new Orders(param))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createCorrectOrder")
    @DisplayName("올바른 주문이 들어온다면 예외가 발생하지 않는다..")
    void 주문_(List<Order> param) {
        new Orders(param);
    }

    @MethodSource
    private static Stream<Arguments> createAbsentNamedOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order("없는 메뉴", 1))),
                arguments(List.of(
                        new Order("없는 메뉴", 1),
                        new Order(BBQ_LIBS.getName(), 2))
                ));
    }

    @MethodSource
    private static Stream<Arguments> createSameOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order(BBQ_LIBS.getName(), 2),
                        new Order(BBQ_LIBS.getName(), 1))
                ),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 2),
                        new Order(ZERO_COKE.getName(), 2))
                )
        );
    }

    @MethodSource
    private static Stream<Arguments> createOverSizeOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order(BBQ_LIBS.getName(), 21)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 10),
                        new Order(BBQ_LIBS.getName(), 10),
                        new Order(SEA_FOOD_PASTA.getName(), 1)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 3),
                        new Order(BBQ_LIBS.getName(), 3),
                        new Order(SEA_FOOD_PASTA.getName(), 5),
                        new Order(CHOCOLATE_CAKE.getName(), 11)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createOnlyDrinkOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 1)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 10),
                        new Order(RED_WINE.getName(), 10)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 3),
                        new Order(RED_WINE.getName(), 3),
                        new Order(CHAMPAGNE.getName(), 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createCorrectOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order(CHOCOLATE_CAKE.getName(), 1)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 10),
                        new Order(CHOCOLATE_CAKE.getName(), 10)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 3),
                        new Order(RED_WINE.getName(), 3),
                        new Order(CHOCOLATE_CAKE.getName(), 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createZeroQuantityOrder() {
        return Stream.of(
                arguments(List.of(
                        new Order(CHOCOLATE_CAKE.getName(), 0)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 0),
                        new Order(RED_WINE.getName(), 10)
                )),
                arguments(List.of(
                        new Order(ZERO_COKE.getName(), 3),
                        new Order(RED_WINE.getName(), 0),
                        new Order(CHAMPAGNE.getName(), 5)
                ))
        );
    }
}