package christmas.controller;

import christmas.domain.Orders;
import christmas.domain.discounts.DiscountCalculator;
import christmas.service.BenefitService;
import christmas.service.DiscountService;
import christmas.service.OrderService;
import christmas.view.inputview.InputValidator;
import christmas.view.inputview.OrderInputView;
import christmas.view.outputview.BenefitOutputView;
import christmas.view.outputview.OrderOutputView;

public final class ControlConfig {
    public Controller createOrderController() {
        return new OrderController(new OrderService(), new OrderInputView(new InputValidator()), new OrderOutputView());
    }

    public Controller createBenefitController(Orders orders) {
        return new BenefitController(
                new BenefitService(new DiscountCalculator(orders), orders.getOriginalPrice()),
                new DiscountService(new DiscountCalculator(orders)),
                new BenefitOutputView());
    }
}
