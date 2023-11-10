package christmas.util;

public final class ErrorHandler {
    private ErrorHandler() {
    }

    private static final String PREFIX = "[ERROR]";
    private static final String INVALID_ORDER = "유효하지 않은 주문입니다. 다시 입력해 주세요";
    private static final String INVALID_DATE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";

    public static void tryUntilNoError(Runnable method, String errorMessage) {
        try {
            method.run();
        } catch (IllegalArgumentException e) {
            System.out.println(PREFIX + errorMessage);
            tryUntilNoError(method, errorMessage);
        }
    }
}
