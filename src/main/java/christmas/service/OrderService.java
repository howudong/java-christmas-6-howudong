package christmas.service;

import christmas.domain.Calendar;
import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.dto.OrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class OrderService {
    public OrderDto.Input createOrderInput() {
        return new OrderDto.Input();
    }

    public OrderDto.Output createOrderOutput(OrderDto.Input dto) {
        Map<String, Integer> order = dto.getOrderProducts();
        int orderDay = dto.getDay();

        return new OrderDto.Output(order, orderDay);
    }

    public Long getOriginalTotalPrice(OrderDto.Input dto) {
        List<OrderProduct> orders = convert(dto.getOrderProducts());
        int orderDay = dto.getDay();
        return getOrders(orders, orderDay).getOriginalPrice();
    }

    private List<OrderProduct> convert(Map<String, Integer> orderProducts) {
        List<OrderProduct> orders = new ArrayList<>();

        orderProducts.forEach((key, value)
                -> orders.add(new OrderProduct(key, value)));

        return orders;
    }

    private Orders getOrders(List<OrderProduct> orders, int orderDay) {
        return new Orders(orders, Calendar.DECEMBER, orderDay);
    }
}
