package christmas.controller;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;

import java.util.HashMap;
import java.util.Map;

public final class MainController {
    private final ControlConfig config;

    public MainController(ControlConfig config) {
        this.config = config;
    }

    public void run() {
        Map<String, InputDto> inputs = new HashMap<>();
        Map<String, OutputDto> outputs = new HashMap<>();
        
        controlOrder(inputs, outputs);
    }

    private void controlOrder(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        Controller orderController = config.createOrderController();
        orderController.process(inputs, outputs);
    }
}
