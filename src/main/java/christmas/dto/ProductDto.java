package christmas.dto;

import christmas.domain.OrderProduct;
import christmas.domain.vo.Product;

public final class ProductDto extends OutputDto {
    private final String name;
    private final Integer quantity;

    public ProductDto(String name, Integer quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public OrderProduct toEntity() {

        return new OrderProduct(Product.findSameProduct(name), quantity);
    }

    public String getName() {
        return name;
    }

    public Integer getQuantity() {
        return quantity;
    }
}
