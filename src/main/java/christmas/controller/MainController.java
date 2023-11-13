package christmas.controller;

import christmas.dto.InputDto;
import christmas.dto.OrderDto;
import christmas.dto.OutputDto;

import java.util.HashMap;
import java.util.Map;

import static christmas.view.Parameter.Output.ORDER_DTO;

public final class MainController {
    private final ControlConfig config;

    public MainController(ControlConfig config) {
        this.config = config;
    }

    public void run() {
        Map<String, InputDto> inputs = new HashMap<>();
        Map<String, OutputDto> outputs = new HashMap<>();

        controlOrder(inputs, outputs);
        controlBenefit(inputs, outputs);
    }

    private void controlOrder(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        Controller orderController = config.createOrderController();
        orderController.process(inputs, outputs);
    }

    private void controlBenefit(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        OrderDto.Output outputDto = (OrderDto.Output) outputs.get(ORDER_DTO);
        Controller benefitController = config.createBenefitController(outputDto.toEntity());
        benefitController.process(inputs, outputs);
    }
}
