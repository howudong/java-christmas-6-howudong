package christmas.service;

import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.Product;
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

        return new OrderDto.Output(order, orderDay, getOriginalTotalPrice(dto));
    }

    private Long getOriginalTotalPrice(OrderDto.Input dto) {
        List<OrderProduct> orders = convert(dto.getOrderProducts());
        int orderDay = dto.getDay();
        return getOrders(orders, orderDay).calculateTotalPrice();
    }

    private List<OrderProduct> convert(Map<String, Integer> orderProducts) {
        List<OrderProduct> orders = new ArrayList<>();

        orderProducts.forEach((key, value)
                -> orders.add(new OrderProduct(Product.findProductByName(key), value)));

        return orders;
    }

    private Orders getOrders(List<OrderProduct> orders, int orderDay) {
        return new Orders(orders, new EventCalendar(orderDay));
    }
}
