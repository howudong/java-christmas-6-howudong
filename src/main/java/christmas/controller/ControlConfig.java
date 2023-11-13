package christmas.controller;

import christmas.service.OrderService;
import christmas.view.inputview.InputValidator;
import christmas.view.inputview.OrderInputView;
import christmas.view.outputview.OrderOutputView;

public final class ControlConfig {
    public Controller createOrderController() {
        return new OrderController(new OrderService(), new OrderInputView(new InputValidator()), new OrderOutputView());
    }
}
