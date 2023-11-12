package christmas.service;

import christmas.dto.OrderDto;

import java.util.Map;

public final class OrderService {
    public OrderDto.Output createOrderOutput(OrderDto.Input dto) {
        Map<String, Integer> order = dto.orderProducts();
        int orderDay = dto.day();

        return new OrderDto.Output(order, orderDay);
    }
}
