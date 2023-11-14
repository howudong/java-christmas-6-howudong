package christmas.domain;

import christmas.util.ErrorHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.vo.Product.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrdersTest {
    @Test
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void null_Order_예외() {
        assertThatThrownBy(() -> new Orders(List.of(), 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }


    @ParameterizedTest
    @MethodSource("createSameOrder")
    @DisplayName("같은 이름의 메뉴를 주문하면 예외가 발생한다.")
    void 메뉴_중복_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOverSizeOrder")
    @DisplayName("주문 개수가 20개가 넘어가면 예외를 발생시킨다.")
    void 수량_초과_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOnlyDrinkOrder")
    @DisplayName("DRINK만 주문하면 예외가 발생한다.")
    void 오직_마실것_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createCorrectOrder")
    @DisplayName("올바른 주문이 들어온다면 예외가 발생하지 않는다.")
    void 주문_올바름(List<OrderProduct> param) {
        new Orders(param, 1);
    }

    @ParameterizedTest
    @MethodSource("createInvalidDate")
    @DisplayName("날짜가 틀렸다면 예이가 발생한다.")
    void 날짜_틀림(int day) {
        assertThatThrownBy(() -> new Orders(List.of(new OrderProduct(CHOCOLATE_CAKE, 1)),
                day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_DATE);
    }

    @MethodSource
    private static Stream<Arguments> createSameOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(BBQ_LIBS, 2),
                        new OrderProduct(BBQ_LIBS, 1))
                ),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 2),
                        new OrderProduct(ZERO_COKE, 2))
                )
        );
    }

    @MethodSource
    private static Stream<Arguments> createOverSizeOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(BBQ_LIBS, 21)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 10),
                        new OrderProduct(BBQ_LIBS, 10),
                        new OrderProduct(SEA_FOOD_PASTA, 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 3),
                        new OrderProduct(BBQ_LIBS, 3),
                        new OrderProduct(SEA_FOOD_PASTA, 5),
                        new OrderProduct(CHOCOLATE_CAKE, 11)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createOnlyDrinkOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 10),
                        new OrderProduct(RED_WINE, 10)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 3),
                        new OrderProduct(RED_WINE, 3),
                        new OrderProduct(CHAMPAGNE, 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createCorrectOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(CHOCOLATE_CAKE, 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 10),
                        new OrderProduct(CHOCOLATE_CAKE, 10)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE, 3),
                        new OrderProduct(RED_WINE, 3),
                        new OrderProduct(CHOCOLATE_CAKE, 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createInvalidDate() {
        return Stream.of(
                arguments(32),
                arguments(0),
                arguments(-1)
        );
    }
}