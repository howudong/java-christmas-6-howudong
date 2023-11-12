package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static christmas.domain.Product.BBQ_LIBS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BenefitTest {
    @Test
    @DisplayName("할인전 금액이 120,000원 미만이라면 null을 반환한다.")
    void 증정_상품_없음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)),
                Calendar.DECEMBER, 1);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), null);
        //when
        Product bonusProduct = benefit.getRewardProduct();
        //then
        assertThat(bonusProduct).isNull();
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 이상이라면 샴페인을 반환한다.")
    void 증정_상품_있음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)),
                Calendar.DECEMBER, 1);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), null);
        //when
        Product bonusProduct = benefit.getRewardProduct();
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

}