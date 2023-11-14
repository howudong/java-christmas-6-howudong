package christmas.domain;

import christmas.domain.discounts.DiscountCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class BenefitTest {
    @Test
    @DisplayName("할인전 금액이 120,000원 미만이라면 null을 반환한다.")
    void 증정_상품_없음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)), 1);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), null);
        //when
        OrderProduct bonusProduct = benefit.getRewardProduct();
        //then
        assertThat(bonusProduct).isNull();
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 이상이라면 샴페인을 반환한다.")
    void 증정_상품_있음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)), 1);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), null);
        //when
        OrderProduct bonusProduct = benefit.getRewardProduct();
        //then
        assertThat(bonusProduct.name()).isEqualTo(Product.CHAMPAGNE.getName());
    }

    @Test
    @DisplayName("혜택 금액이 20,000원 이상이라면 산타 배지만 반환해야한다.")
    void 산타_배지() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        BadgeType rewardBadge = benefit.getRewardBadge();
        //then
        assertThat(rewardBadge).isEqualTo(BadgeType.SANTA);
    }

    @Test
    @DisplayName("혜택 금액이 20,000원 미만, 10000원 이상이라면 트리 배지만 반환해야한다.")
    void 트리_배지() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.CHOCOLATE_CAKE.getName(), 5)), 3);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        BadgeType rewardBadge = benefit.getRewardBadge();
        //then
        assertThat(rewardBadge).isEqualTo(BadgeType.TREE);
    }

    @Test
    @DisplayName("혜택 금액이 10,000원 미만, 5000원 이상이라면 트리 배지만 반환해야한다.")
    void 스타_배지() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 3)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        BadgeType rewardBadge = benefit.getRewardBadge();
        //then
        assertThat(rewardBadge).isEqualTo(BadgeType.STAR);
    }

    @Test
    @DisplayName("혜택 금액이 5000원 미만이라면 null을 반환해야한다.")
    void 배지_없음() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        BadgeType rewardBadge = benefit.getRewardBadge();
        //then
        assertThat(rewardBadge).isNull();
    }

    @Test
    @DisplayName("증정품으로 샴페인이 있다면 DiscountMap에 포함되어 있어야한다.")
    void 할인_목록_증정_상품_포함() {
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        Map<String, Long> discounts = benefit.getDiscounts();
        //then
        assertThat(discounts).containsKey("증정 상품");
    }

    @Test
    @DisplayName("증정품으로 샴페인이 없다면 DiscountMap에 포함되어 있어야한다.")
    void 할인_목록_증정_상품_포함X() {
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        Map<String, Long> discounts = benefit.getDiscounts();
        //then
        assertThat(discounts).doesNotContainKey("증정 상품");
    }

    @Test
    @DisplayName("혜택 금액에 증정품이 포함되어있다면 그 가격도 포함되어야한다.")
    void 혜택_금액_증정_포함() {
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        Long result = benefit.getBenefitPrice();
        //then
        assertThat(result).isEqualTo(2023 * 10 + Product.CHAMPAGNE.getPrice() + 1000);
    }

    @Test
    @DisplayName("혜택 금액에 증정품이 포함되어있지않다면 그 가격이 제외되어야한다.")
    void 혜택_금액_증정_포함X() {
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)), 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        Long result = benefit.getBenefitPrice();
        //then
        assertThat(result).isEqualTo(2023 * 1 + 1000);
    }
}