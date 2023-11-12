package christmas.util;

import christmas.view.ErrorHandlerView;

public final class ErrorHandler {
    private static final String PREFIX = "[ERROR]";

    public static final String INVALID_ORDER = "유효하지 않은 주문입니다. 다시 입력해 주세요";
    public static final String INVALID_DATE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";

    private ErrorHandler() {
    }

    public static void tryUntilNoError(Runnable method, String errorMessage) {
        try {
            method.run();
        } catch (IllegalArgumentException e) {
            ErrorHandlerView.view(PREFIX + errorMessage);
            tryUntilNoError(method, errorMessage);
        }
    }

    public static String getText(String errorMessage) {
        return PREFIX + errorMessage;
    }
}
