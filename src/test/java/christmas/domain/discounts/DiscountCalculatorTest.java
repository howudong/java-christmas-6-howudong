package christmas.domain.discounts;

import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DiscountCalculatorTest {

    @Test
    @DisplayName("10000원 미만일때 아무런 할인도 반환해선 안된다.")
    void 만원_미만_할인_없음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.MUSHROOM_SOUP, 1)), new EventCalendar(1));
        DiscountCalculator strategy = new DiscountCalculator(orders);
        //when
        Map<String, Long> availableDiscounts = strategy.getAvailableDiscounts();
        //then
        assertThat(availableDiscounts).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("createNoChristmas")
    @DisplayName("주문이 12월 25일 초과면 크리스마스 할인을 포함해선 안된다.")
    void 크리스마스_할인_기간_X(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.MUSHROOM_SOUP, 1)), new EventCalendar(day));
        DiscountCalculator strategy = new DiscountCalculator(orders);
        //when
        Map<String, Long> availableDiscounts = strategy.getAvailableDiscounts();
        //then
        assertThat(availableDiscounts).doesNotContainKey("크리스마스 디데이 할인");
    }

    @ParameterizedTest
    @MethodSource("createWeekend")
    @DisplayName("주문이 주말이면 주말 할인은 포함하고, 평일 할인은 포함해선 안된다.")
    void 주말_주문_할인(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA, 2)), new EventCalendar(day));
        DiscountCalculator strategy = new DiscountCalculator(orders);
        //when
        Map<String, Long> availableDiscounts = strategy.getAvailableDiscounts();
        //then
        assertThat(availableDiscounts).doesNotContainKey("평일 할인").containsKey("주말 할인");
    }

    @ParameterizedTest
    @MethodSource("createWeekday")
    @DisplayName("주문이 평일이면 평일 할인은 포함하고, 주말 할인은 포함해선 안된다.")
    void 평일_주문_할인(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE, 2)), new EventCalendar(day));
        DiscountCalculator strategy = new DiscountCalculator(orders);
        //when
        Map<String, Long> availableDiscounts = strategy.getAvailableDiscounts();
        //then
        assertThat(availableDiscounts).doesNotContainKey("주말 할인").containsKey("평일 할인");
    }

    @ParameterizedTest
    @MethodSource("createSpecial")
    @DisplayName("Star 날이면 특별 할인이 적용된다.")
    void 특별_할인(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE, 2)), new EventCalendar(day));
        DiscountCalculator strategy = new DiscountCalculator(orders);
        //when
        Map<String, Long> availableDiscounts = strategy.getAvailableDiscounts();
        //then
        assertThat(availableDiscounts).containsKey("특별 할인");
    }

    @MethodSource
    private static Stream<Arguments> createNoChristmas() {
        return Stream.of(
                Arguments.of(26),
                Arguments.of(27));
    }

    @MethodSource
    private static Stream<Arguments> createWeekend() {
        return Stream.of(
                Arguments.of(1),
                Arguments.of(2),
                Arguments.of(8),
                Arguments.of(9),
                Arguments.of(15),
                Arguments.of(16));
    }

    @MethodSource
    private static Stream<Arguments> createWeekday() {
        return Stream.of(
                Arguments.of(3),
                Arguments.of(4),
                Arguments.of(5),
                Arguments.of(6),
                Arguments.of(7),
                Arguments.of(18));
    }

    @MethodSource
    private static Stream<Arguments> createSpecial() {
        return Stream.of(
                Arguments.of(3),
                Arguments.of(10),
                Arguments.of(24),
                Arguments.of(25));
    }
}