package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class GoodsProviderTest {
    private final GoodsProvider goodsProvider = new GoodsProvider();

    @Test
    @DisplayName("할인전 금액이 120,000원 미만이라면 null을 반환한다.")
    void 증정_상품_없음() {
        //given,when
        Product bonusProduct = goodsProvider.getBonusProduct(119_999L);
        //then
        assertThat(bonusProduct).isNull();
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 이상이라면 샴페인을 반환한다.")
    void 증정_상품_있음() {
        //given,when
        Product bonusProduct = goodsProvider.getBonusProduct(120_000L);
        //then
        assertThat(bonusProduct).isEqualTo(Product.CHAMPAGNE);
    }

    @Test
    @DisplayName("혜택 금액이 20,000원 이상이라면 산타 배지만 반환해야한다.")
    void 산타_배지() {
        //given,when
        BadgeType rewardBadge = goodsProvider.getRewardBadge(20_000L);
        //then
        assertThat(rewardBadge).isEqualTo(BadgeType.SANTA);
    }

    @Test
    @DisplayName("혜택 금액이 20,000원 미만, 10000원 이상이라면 트리 배지만 반환해야한다.")
    void 트리_배지() {
        //given
        List<Long> param = List.of(19_999L, 10_000L);
        //when
        List<BadgeType> badges = param.stream().map(goodsProvider::getRewardBadge).toList();
        //then
        assertThat(badges).isNotEmpty().allMatch(e -> e.equals(BadgeType.TREE));
    }

    @Test
    @DisplayName("혜택 금액이 10,000원 미만, 5000원 이상이라면 트리 배지만 반환해야한다.")
    void 스타_배지() {
        //given
        List<Long> param = List.of(9_999L, 5_000L);
        //when
        List<BadgeType> badges = param.stream().map(goodsProvider::getRewardBadge).toList();
        //then
        assertThat(badges).isNotEmpty().allMatch(e -> e.equals(BadgeType.STAR));
    }

    @Test
    @DisplayName("혜택 금액이 5000원 미만이라면 null을 반환해야한다.")
    void 배지_없음() {
        //given,when
        BadgeType rewardBadge = goodsProvider.getRewardBadge(4_999L);
        //then
        assertThat(rewardBadge).isNull();
    }
}