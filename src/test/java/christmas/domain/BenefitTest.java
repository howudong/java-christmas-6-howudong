package christmas.domain;

import christmas.domain.discounts.DiscountCalculator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    @DisplayName("혜택 금액이 20,000원 이상이라면 산타 배지만 반환해야한다.")
    void 산타_배지() {
        //given
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 10)),
                Calendar.DECEMBER, 1);
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
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 5)),
                Calendar.DECEMBER, 1);
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
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 3)),
                Calendar.DECEMBER, 1);
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
        Orders orders = new Orders(List.of(new OrderProduct(Product.SEA_FOOD_PASTA.getName(), 1)),
                Calendar.DECEMBER, 1);
        DiscountCalculator strategy = new DiscountCalculator(orders);
        Benefit benefit = new Benefit(orders.getOriginalPrice(), strategy.getAvailableDiscounts());
        //when
        BadgeType rewardBadge = benefit.getRewardBadge();
        //then
        assertThat(rewardBadge).isNull();
    }
}