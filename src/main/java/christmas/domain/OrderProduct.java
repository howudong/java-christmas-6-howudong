package christmas.domain;

public final class OrderProduct {
    private final Products product;
    private final int quantity;

    public OrderProduct(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Products getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}
