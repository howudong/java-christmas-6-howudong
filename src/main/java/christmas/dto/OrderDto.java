package christmas.dto;

import java.util.Map;
import java.util.Objects;

public final class OrderDto {
    private OrderDto() {
    }

    public static final class Input {
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

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != this.getClass()) return false;
            var that = (Input) obj;
            return Objects.equals(this.orderProducts, that.orderProducts) &&
                    this.day == that.day;
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderProducts, day);
        }

        @Override
        public String toString() {
            return "Input[" +
                    "orderProducts=" + orderProducts + ", " +
                    "day=" + day + ']';
        }

    }

    public record Output(Map<String, Integer> orderProducts, int day) {
    }
}
