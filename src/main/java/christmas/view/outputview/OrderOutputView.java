package christmas.view.outputview;

import christmas.dto.InputDto;
import christmas.dto.OrderDto;
import christmas.dto.OutputDto;
import christmas.view.Parameter;

import java.util.Map;

import static christmas.view.Parameter.Output.ORDER_DTO;

public final class OrderOutputView implements OutputView {
    private static final String ERROR_KEYWORD = "[ERROR]";
    
    private final Map<String, Runnable> textMethods = Map.ofEntries(
            Map.entry(Parameter.Input.ORDER_DAY, this::viewOrderDay),
            Map.entry(Parameter.Input.ORDER_PRODUCTS, this::viewOrderProduct)
    );

    public OrderOutputView() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    @Override
    public void view(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        if (isHasErrorText(outputs)) {
            findAndViewErrorText(outputs);
            return;
        }
        inputs.keySet().forEach(this::runTextMethod);
        if (outputs.containsKey(ORDER_DTO)) {
            viewOrderMenuAndPrice(outputs.get(ORDER_DTO));
        }
    }

    private boolean isHasErrorText(Map<String, OutputDto> outputs) {
        return outputs.keySet()
                .stream()
                .anyMatch(e -> e.contains(ERROR_KEYWORD));
    }

    private void findAndViewErrorText(Map<String, OutputDto> outputs) {
        String text = outputs.keySet()
                .stream()
                .filter(e -> e.contains(ERROR_KEYWORD))
                .findFirst()
                .orElse(null);

        System.out.println(text);
        outputs.remove(text);
    }

    private void runTextMethod(String name) {
        if (textMethods.containsKey(name)) {
            textMethods.get(name).run();
        }
    }

    private void viewOrderDay() {
        System.out.println("12월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)");
    }

    private void viewOrderProduct() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. " +
                "(e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
    }

    private void viewOrderMenuAndPrice(OutputDto dto) {
        OrderDto.Output orderOutputDto = (OrderDto.Output) dto;
        System.out.println("12월 " + orderOutputDto.day() + "일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n");

        viewOrderMenu(orderOutputDto);
        System.out.println();

        viewOriginalTotalPrice(orderOutputDto);
        System.out.println();

    }

    private void viewOrderMenu(OrderDto.Output orderOutputDto) {
        System.out.println("<주문 메뉴>");
        Map<String, Integer> orderProducts = orderOutputDto.orderProducts();
        orderProducts.forEach((key, value) -> System.out.println(key + " " + value + "개"));
    }

    private void viewOriginalTotalPrice(OrderDto.Output orderOutputDto) {
        System.out.println("<할인 전 총주문 금액>");
        Long price = orderOutputDto.originalTotalPrice();
        System.out.println(convertToPay(price));
    }

    private String convertToPay(Long pay) {
        return String.format("%,d원", pay);
    }
}
