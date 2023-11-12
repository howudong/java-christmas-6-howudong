package christmas.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RewardProviderTest {
    private final RewardProvider rewardProvider = new RewardProvider();

    @Test
    @DisplayName("할인전 금액이 120,000원 미만이라면 null을 반환한다.")
    void 증정_상품_없음() {
        //given,when
        Product bonusProduct = rewardProvider.getBonusProduct(119_999L);
        //then
        assertThat(bonusProduct).isNull();
    }

    @Test
    @DisplayName("할인전 금액이 120,000원 이상이라면 샴페인을 반환한다.")
    void 증정_상품_있음() {
        //given,when
        Product bonusProduct = rewardProvider.getBonusProduct(120_000L);
        //then
        assertThat(bonusProduct).isEqualTo(Product.CHAMPAGNE);
    }
}