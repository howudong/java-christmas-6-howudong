package christmas.view;

public final class Parameter {
    private Parameter() {
    }

    public static class Input {
        private Input() {
        }

        public static final String ORDER_DAY = "orderDay";
        public static final String ORDER_PRODUCTS = "orderProducts";
        public static final String ORDER_INPUT_DTO = "orderInputDto";
    }

    public static class Output {
        private Output() {
        }

        public static final String ORDER_DTO = "orderDto";
        public static final String BENEFIT_DTO = "benefitDto";
        public static final String DISCOUNT_DTO = "discountDto";
    }
}
