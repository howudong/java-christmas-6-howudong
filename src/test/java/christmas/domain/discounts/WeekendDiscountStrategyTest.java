package christmas.domain.discounts;

import christmas.domain.Calendar;
import christmas.domain.MenuType;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.Product.*;
import static org.assertj.core.api.Assertions.assertThat;

class WeekendDiscountStrategyTest {
    private final DiscountStrategy strategy = new WeekendDiscountStrategy();

    @ParameterizedTest
    @MethodSource("createWeekendOrder")
    @DisplayName("주말 할인 가능 날짜가 들어오면 주말 할인을 진행한다.")
    void 주말_할인_정상(int day, List<OrderProduct> orderList) {
        //given
        Orders orders = new Orders(orderList, Calendar.DECEMBER, day);
        // when
        Long discount = strategy.discount(orders);
        //then
        Integer size = orderList.stream()
                .filter(e -> MenuType.getMenuTypeByName(e.name()).equals(MenuType.MAIN))
                .map(OrderProduct::quantity)
                .reduce(Integer::sum)
                .orElse(0);

        assertThat(discount).isEqualTo(size * 2023L);
    }

    @MethodSource
    private static Stream<Arguments> createWeekendOrder() {
        return Stream.of(
                Arguments.of(1, List.of(new OrderProduct(CHRISTMAS_PASTA.getName(), 1))),
                Arguments.of(2, List.of(new OrderProduct(SEA_FOOD_PASTA.getName(), 2))),
                Arguments.of(8, List.of(new OrderProduct(CHOCOLATE_CAKE.getName(), 2))),
                Arguments.of(29, List.of(new OrderProduct(SEA_FOOD_PASTA.getName(), 2),
                        new OrderProduct(CHRISTMAS_PASTA.getName(), 2))));
    }
}