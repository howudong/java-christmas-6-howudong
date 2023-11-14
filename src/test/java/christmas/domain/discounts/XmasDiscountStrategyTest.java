package christmas.domain.discounts;

import christmas.domain.DiscountStrategy;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class XmasDiscountStrategyTest {
    private final DiscountStrategy strategy = new XmasDiscountStrategy();

    @Test
    @DisplayName("25일 이후가 들어오면 할인 금액은 0원 이어야 한다.")
    void 날짜_초과_0원_반환() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE.getName(), 1)), 26);
        //when
        Long discount = strategy.discount(orders);
        //then
        assertThat(discount).isZero();
    }

    @ParameterizedTest
    @MethodSource("createParam")
    @DisplayName("일자에 맞춰 할인 금액이 정상적으로 반환되어야 한다.")
    void 정상_금액_반환(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE.getName(), 1)), day);
        //when
        Long discount = strategy.discount(orders);
        //then
        Long expected = 1000L + (orders.orderDay() - 1) * 100L;
        assertThat(discount).isEqualTo(expected);
    }

    @MethodSource
    private static Stream<Arguments> createParam() {
        return Stream.of(
                arguments(24),
                arguments(25),
                arguments(7)
        );
    }
}