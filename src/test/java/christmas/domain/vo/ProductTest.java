package christmas.domain.vo;

import christmas.domain.OrderProduct;
import christmas.util.ErrorManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static christmas.domain.vo.Product.CHOCOLATE_CAKE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProductTest {
    @Test
    @DisplayName("Product에 없는 메뉴의 이름이 포함되어 있다면 예외를 발생시킨다.")
    void 해당_메뉴_없음_예외() {
        assertThatThrownBy(() -> new OrderProduct(null, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorManager.INVALID_ORDER);
    }

    @Test
    @DisplayName("주문 개수가 0개인 것이 있다면 예외를 발생시킨다.")
    void 주문_개수_0개_예외() {
        assertThatThrownBy(() -> new OrderProduct(CHOCOLATE_CAKE, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorManager.INVALID_ORDER);
    }
}