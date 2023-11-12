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
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class OrdersTest {
    @Test
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void null_Order_예외() {
        assertThatThrownBy(() -> new Orders(List.of(), Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createAbsentNamedOrder")
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void 해당_메뉴_없음_예외(List<OrderProduct> orderProducts) {
        assertThatThrownBy(() -> new Orders(orderProducts, Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createSameOrder")
    @DisplayName("같은 이름의 메뉴를 주문하면 예외가 발생한다.")
    void 메뉴_중복_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOverSizeOrder")
    @DisplayName("주문 개수가 20개가 넘어가면 예외를 발생시킨다.")
    void 수량_초과_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createOnlyDrinkOrder")
    @DisplayName("DRINK만 주문하면 예외가 발생한다.")
    void 오직_마실것_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createZeroQuantityOrder")
    @DisplayName("주문 개수가 0개인 것이 있다면 예외를 발생시킨다.")
    void 주문_개수_0개_예외(List<OrderProduct> param) {
        assertThatThrownBy(() -> new Orders(param, Calendar.DECEMBER, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);
    }

    @ParameterizedTest
    @MethodSource("createCorrectOrder")
    @DisplayName("올바른 주문이 들어온다면 예외가 발생하지 않는다..")
    void 주문_올바름(List<OrderProduct> param) {
        new Orders(param, Calendar.DECEMBER, 1);
    }

    @ParameterizedTest
    @MethodSource("createInvalidDate")
    @DisplayName("올바른 주문이 들어온다면 예외가 발생하지 않는다..")
    void 주문_올바름(int day) {
        assertThatThrownBy(() -> new Orders(List.of(new OrderProduct(CHOCOLATE_CAKE.getName(), 1)),
                Calendar.DECEMBER, day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_DATE);
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 미만이라면 null을 반환한다.")
    void 증정_상품_없음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)),
                Calendar.DECEMBER, 1);
        //when
        Product bonusProduct = orders.getBonusProduct();
        //then
        assertThat(bonusProduct).isNull();
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 이상이라면 샴페인을 반환한다.")
    void 증정_상품_있음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)),
                Calendar.DECEMBER, 1);
        //when
        Product bonusProduct = orders.getBonusProduct();
        //then
        assertThat(bonusProduct).isEqualTo(Product.CHAMPAGNE);
    }

    @MethodSource
    private static Stream<Arguments> createAbsentNamedOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct("없는 메뉴", 1))),
                arguments(List.of(
                        new OrderProduct("없는 메뉴", 1),
                        new OrderProduct(BBQ_LIBS.getName(), 2))
                ));
    }

    @MethodSource
    private static Stream<Arguments> createSameOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(BBQ_LIBS.getName(), 2),
                        new OrderProduct(BBQ_LIBS.getName(), 1))
                ),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 2),
                        new OrderProduct(ZERO_COKE.getName(), 2))
                )
        );
    }

    @MethodSource
    private static Stream<Arguments> createOverSizeOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(BBQ_LIBS.getName(), 21)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 10),
                        new OrderProduct(BBQ_LIBS.getName(), 10),
                        new OrderProduct(SEA_FOOD_PASTA.getName(), 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 3),
                        new OrderProduct(BBQ_LIBS.getName(), 3),
                        new OrderProduct(SEA_FOOD_PASTA.getName(), 5),
                        new OrderProduct(CHOCOLATE_CAKE.getName(), 11)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createOnlyDrinkOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 10),
                        new OrderProduct(RED_WINE.getName(), 10)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 3),
                        new OrderProduct(RED_WINE.getName(), 3),
                        new OrderProduct(CHAMPAGNE.getName(), 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createCorrectOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(CHOCOLATE_CAKE.getName(), 1)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 10),
                        new OrderProduct(CHOCOLATE_CAKE.getName(), 10)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 3),
                        new OrderProduct(RED_WINE.getName(), 3),
                        new OrderProduct(CHOCOLATE_CAKE.getName(), 5)
                ))
        );
    }

    @MethodSource
    private static Stream<Arguments> createZeroQuantityOrder() {
        return Stream.of(
                arguments(List.of(
                        new OrderProduct(CHOCOLATE_CAKE.getName(), 0)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 0),
                        new OrderProduct(RED_WINE.getName(), 10)
                )),
                arguments(List.of(
                        new OrderProduct(ZERO_COKE.getName(), 3),
                        new OrderProduct(RED_WINE.getName(), 0),
                        new OrderProduct(CHAMPAGNE.getName(), 5)
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