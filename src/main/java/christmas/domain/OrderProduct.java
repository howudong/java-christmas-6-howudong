package christmas.domain;

import christmas.domain.vo.MenuType;
import christmas.domain.vo.Product;

import java.util.Objects;

import static christmas.util.ErrorHandler.INVALID_ORDER;
import static christmas.util.ErrorHandler.getText;

public final class OrderProduct {
    private final Product product;
    private final int quantity;

    public OrderProduct(Product product, int quantity) {
        validateExist(product);
        validateEmptyQuantity(quantity);
        this.product = product;
        this.quantity = quantity;
    }

    public Long sumOrderProduct() {
        return product.getPrice() * quantity;
    }

    public MenuType findMenuType() {
        return MenuType.findMenuTypeProduct(product);
    }

    public int findSameMenuTypeQuantity(MenuType menuType) {
        MenuType menuTypeProduct = MenuType.findMenuTypeProduct(product);
        if (menuTypeProduct.equals(menuType)) {
            return quantity;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderProduct that = (OrderProduct) o;
        return product == that.product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }

    int getQuantity() {
        return quantity;
    }

    private void validateEmptyQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }

    private void validateExist(Product product) {
        if (product == null || Product.findSameProduct(product.getName()) == null) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
    }
}
