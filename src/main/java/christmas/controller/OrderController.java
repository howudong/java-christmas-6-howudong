package christmas.controller;

import christmas.domain.OrderProduct;
import christmas.domain.Orders;
import christmas.domain.vo.EventCalendar;
import christmas.domain.vo.Product;
import christmas.dto.InputDto;
import christmas.dto.OrderDto;
import christmas.dto.OutputDto;
import christmas.service.OrderService;
import christmas.view.inputview.InputView;
import christmas.view.outputview.OutputView;

import java.util.List;
import java.util.Map;

import static christmas.util.ErrorManager.*;
import static christmas.view.Parameter.Input.*;
import static christmas.view.Parameter.Output.ERROR;
import static christmas.view.Parameter.Output.ORDER_DTO;

public final class OrderController implements Controller {
    private static final List<OrderProduct> FAKE_ORDER_PRODUCTS =
            List.of(new OrderProduct(Product.CHOCOLATE_CAKE, 1));
    private final OrderService orderService;
    private final InputView inputView;
    private final OutputView outputView;

    public OrderController(OrderService orderService, InputView inputView, OutputView outputView) {
        this.orderService = orderService;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    @Override
    public void process(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        inputs.put(ORDER_INPUT_DTO, orderService.createOrderInput());

        tryUntilNoError(
                () -> displayOrderDayView(inputs, outputs),
                () -> displayErrorView(inputs, outputs, getText(INVALID_DATE)));
        tryUntilNoError(
                () -> displayOrderProductsView(inputs, outputs),
                () -> displayErrorView(inputs, outputs, getText(INVALID_ORDER)));
        tryUntilNoError(
                () -> displayOrderResultView(inputs, outputs),
                () -> displayErrorView(inputs, outputs, getText(INVALID_ORDER)));
    }

    private void displayOrderDayView(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        inputs.put(ORDER_DAY, null);
        outputView.view(inputs, outputs);
        inputView.read(inputs);
        validateDay(inputs.get(ORDER_INPUT_DTO));
    }

    private void validateDay(InputDto inputDto) {
        OrderDto.Input dto = (OrderDto.Input) inputDto;
        int day = dto.getDay();
        new Orders(FAKE_ORDER_PRODUCTS, new EventCalendar(day));
    }

    private void displayOrderProductsView(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        inputs.put(ORDER_PRODUCTS, null);
        outputView.view(inputs, outputs);
        inputView.read(inputs);
    }

    private void displayOrderResultView(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        OrderDto.Input inputDto = (OrderDto.Input) inputs.get(ORDER_INPUT_DTO);
        if (inputDto == null) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }
        OrderDto.Output orderOutput = orderService.createOrderOutput(inputDto);
        outputs.put(ORDER_DTO, orderOutput);
        outputView.view(inputs, outputs);
    }

    private void displayErrorView(Map<String, InputDto> inputs, Map<String, OutputDto> outputs, String errorText) {
        outputs.put(errorText, null);
        outputView.view(inputs, outputs);
        outputs.remove(ERROR);
    }
}
