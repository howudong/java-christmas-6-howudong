package christmas.dto;

import christmas.domain.Calendar;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OrderDto {
    private OrderDto() {
    }

    public static final class Input extends InputDto {
        private Map<String, Integer> orderProducts;
        private int day;

        public void setOrderProducts(Map<String, Integer> orderProducts) {
            this.orderProducts = orderProducts;
        }

        public void setDay(int day) {
            this.day = day;
        }

        public Map<String, Integer> getOrderProducts() {
            return orderProducts;
        }

        public int getDay() {
            return day;
        }
    }

    public static final class Output extends OutputDto {
        private final Map<String, Integer> orderProducts;
        private final int day;
        private final Long originalTotalPrice;

        public Output(Map<String, Integer> orderProducts, int day, Long originalTotalPrice) {
            this.orderProducts = orderProducts;
            this.day = day;
            this.originalTotalPrice = originalTotalPrice;
        }

        public Orders toEntity() {
            List<OrderProduct> orders = new ArrayList<>();
            orderProducts.forEach((key, value) -> orders.add(new OrderProduct(key, value)));
            return new Orders(orders, Calendar.DECEMBER, day);
        }

        public Map<String, Integer> orderProducts() {
            return orderProducts;
        }

        public int day() {
            return day;
        }

        public Long originalTotalPrice() {
            return originalTotalPrice;
        }
    }
}
