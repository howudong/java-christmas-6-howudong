package christmas.domain.discounts;

import christmas.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.Product.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class WeekdayDiscountStrategyTest {
    private final DiscountStrategy strategy = new WeekdayDiscountStrategy(new EventCalendar());

    @ParameterizedTest
    @MethodSource("createWeekdayOrder")
    @DisplayName("평일 할인 가능 날짜가 들어오면 평일 할인을 진행한다.")
    void 평일_할인_정상(int day, List<OrderProduct> orderList) {
        //given
        Orders orders = new Orders(orderList, day);
        // when
        Long discount = strategy.discount(orders);
        //then
        Integer size = orderList.stream()
                .filter(e -> MenuType.findMenuTypeByName(e.name()).equals(MenuType.DESERT))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        assertThat(discount).isEqualTo(size * 2023L);
    }

    @MethodSource
    private static Stream<Arguments> createWeekdayOrder() {
        return Stream.of(
                arguments(3, List.of(new OrderProduct(CHRISTMAS_PASTA.getName(), 2))),
                arguments(4, List.of(new OrderProduct(CHOCOLATE_CAKE.getName(), 3))),
                arguments(12, List.of(new OrderProduct(CHOCOLATE_CAKE.getName(), 3),
                        new OrderProduct(ICECREAM.getName(), 2))),
                arguments(14, List.of(new OrderProduct(BBQ_LIBS.getName(), 4)))
        );
    }
}