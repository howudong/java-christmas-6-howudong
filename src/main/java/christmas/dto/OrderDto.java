package christmas.dto;

import java.util.Map;

public final class OrderDto {
    private OrderDto() {
    }

    public record Input(Map<String, Integer> orderProducts, int day) {
    }

    public record Output(Map<String, Integer> orderProducts, int day, Long originalTotalPrice) {
    }
}
