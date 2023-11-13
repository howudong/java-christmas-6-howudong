package christmas.view.inputview;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.InputDto;
import christmas.dto.OrderDto;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public final class OrderInputView implements InputView {
    private static final char DELIMITER = '-';
    private static final char PRODUCT_DELIMITER = ',';
    private final InputValidator inputValidator;
    private final Map<String, Consumer<InputDto>> methods = Map.ofEntries(
            Map.entry("orderDay", this::inputOrderDay),
            Map.entry("orderProducts", this::inputOrderProducts)
    );

    public OrderInputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public void read(Map<InputDto, String> inputs) {
        inputs.forEach((dto, name) -> runMethod(inputs, dto, name));
    }

    private void runMethod(Map<InputDto, String> inputs, InputDto dto, String methodName) {
        if (methods.containsKey(methodName)) {
            methods.get(methodName).accept(dto);
        }
        inputs.put(dto, null);
    }

    private void inputOrderDay(InputDto dto) {
        OrderDto.Input inputDto = (OrderDto.Input) dto;
        String input = Console.readLine();
        inputValidator.isNumeric(input);
        inputDto.setDay(Integer.parseInt(input));
    }

    private void inputOrderProducts(InputDto dto) {
        OrderDto.Input inputDto = (OrderDto.Input) dto;
        Map<String, Integer> orders = new HashMap<>();

        String input = Console.readLine();
        String[] splitInput = input.split(String.valueOf(PRODUCT_DELIMITER));

        inputValidator.hasExactlyContains(PRODUCT_DELIMITER, splitInput.length - 1, input);
        validateOrderProduct(orders, splitInput);
        inputDto.setOrderProducts(orders);
    }

    private void validateOrderProduct(Map<String, Integer> orders, String[] splitInput) {
        for (String split : splitInput) {
            validateInputOrder(split);
            addOrderProduct(orders, split);
        }
    }

    private void validateInputOrder(String input) {
        String[] splitInput = input.split(String.valueOf(DELIMITER));

        inputValidator.hasExactlyContains(DELIMITER, 1, input);
        inputValidator.isOnlyWords(splitInput[0]);
        inputValidator.isNumeric(splitInput[1]);
    }

    private void addOrderProduct(Map<String, Integer> orders, String input) {
        String[] splitInput = input.split(String.valueOf(DELIMITER));
        orders.put(splitInput[0].trim(), Integer.parseInt(splitInput[1].trim()));
    }
}
