package christmas.domain.discounts;

import christmas.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class SpecialDiscountStrategyTest {
    private final DiscountStrategy strategy = new SpecialDiscountStrategy(new EventCalendar());

    @ParameterizedTest
    @MethodSource("createStarDay")
    @DisplayName("Star로 된 날짜가 들어오면 1000원을 반환해야한다.")
    void Star_Day_천원_할인(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE.getName(), 1)), day);
        //when
        Long discount = strategy.discount(orders);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    @ParameterizedTest
    @MethodSource("createNotStarDay")
    @DisplayName("Star로 된 날짜가 들어오면 1000원을 반환해야한다.")
    void Not_Star_Day_할인_업음(int day) {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE.getName(), 1)), day);
        //when
        Long discount = strategy.discount(orders);
        //then
        assertThat(discount).isZero();
    }

    @MethodSource
    private static Stream<Arguments> createStarDay() {
        return Stream.of(
                arguments(3),
                arguments(10),
                arguments(17),
                arguments(24),
                arguments(25),
                arguments(31)
        );
    }

    @MethodSource
    private static Stream<Arguments> createNotStarDay() {
        return Stream.of(
                arguments(4),
                arguments(11),
                arguments(1),
                arguments(27),
                arguments(19),
                arguments(15)
        );
    }
}