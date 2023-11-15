package christmas.domain.discounts;

import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.MenuType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.vo.Product.*;
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
        //when
        Long discount = strategy.discount(orders);
        //then
        Integer size = orderList.stream()
                .map(e -> e.findSameMenuTypeQuantity(MenuType.DESERT))
                .reduce(Integer::sum)
                .orElse(0);

        assertThat(discount).isEqualTo(size * 2023L);
    }

    @MethodSource
    private static Stream<Arguments> createWeekdayOrder() {
        return Stream.of(
                arguments(3, List.of(new OrderProduct(CHRISTMAS_PASTA, 2))),
                arguments(4, List.of(new OrderProduct(CHOCOLATE_CAKE, 3))),
                arguments(12, List.of(new OrderProduct(CHOCOLATE_CAKE, 3),
                        new OrderProduct(ICE_CREAM, 2))),
                arguments(14, List.of(new OrderProduct(BBQ_LIBS, 4)))
        );
    }
}