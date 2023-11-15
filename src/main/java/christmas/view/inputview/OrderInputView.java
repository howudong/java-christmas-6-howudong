package christmas.view.inputview;

import christmas.dto.InputDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import static camp.nextstep.edu.missionutils.Console.readLine;
import static christmas.dto.OrderDto.Input;
import static christmas.util.ErrorHandler.INVALID_ORDER;
import static christmas.util.ErrorHandler.getText;
import static christmas.view.Parameter.Input.ORDER_INPUT_DTO;

public final class OrderInputView implements InputView {
    private static final char DELIMITER = '-';
    private static final char PRODUCT_DELIMITER = ',';
    private static final String EMPTY_SPACE = " ";
    private static final String REMOVE = "";

    private final InputValidator inputValidator;
    private final Map<String, Consumer<Map<String, InputDto>>> methods =
            Map.ofEntries(
                    Map.entry("orderDay", this::inputOrderDay),
                    Map.entry("orderProducts", this::inputOrderProducts)
            );

    public OrderInputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public void read(Map<String, InputDto> inputs) {
        if (!inputs.containsKey(ORDER_INPUT_DTO)) {
            throw new IllegalArgumentException(getText(INVALID_ORDER));
        }

        inputs.keySet().forEach(name -> runMethod(inputs, name));
        inputs.values().removeIf(Objects::isNull);
    }

    private void runMethod(Map<String, InputDto> inputs, String name) {
        if (methods.containsKey(name)) {
            methods.get(name).accept(inputs);
        }
    }

    private void inputOrderDay(Map<String, InputDto> inputs) {
        Input dto = (Input) inputs.get(ORDER_INPUT_DTO);
        String input = removeEmptySpace(readLine());
        inputValidator.isNumeric(input);

        dto.setDay(Integer.parseInt(input));
    }

    private void inputOrderProducts(Map<String, InputDto> inputs) {
        Map<String, Integer> orders = new HashMap<>();
        Input dto = (Input) inputs.get(ORDER_INPUT_DTO);

        String input = removeEmptySpace(readLine());
        String[] splitInput = input.split(String.valueOf(PRODUCT_DELIMITER));

        inputValidator.hasExactlyContains(PRODUCT_DELIMITER, splitInput.length - 1, input);
        validateOrderProduct(orders, splitInput);
        dto.setOrderProducts(orders);
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

    private String removeEmptySpace(String input) {
        return input.replace(EMPTY_SPACE, REMOVE);
    }
}
