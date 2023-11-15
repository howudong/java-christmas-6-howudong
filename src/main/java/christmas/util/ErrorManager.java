package christmas.util;

public final class ErrorManager {
    private static final String PREFIX = "[ERROR] ";
    public static final String INVALID_ORDER = "유효하지 않은 주문입니다. 다시 입력해 주세요.";
    public static final String INVALID_DATE = "유효하지 않은 날짜입니다. 다시 입력해 주세요.";

    private ErrorManager() {
    }

    public static void tryUntilNoError(Runnable runMethod, Runnable methodOnExcept) {
        try {
            runMethod.run();
        } catch (IllegalArgumentException e) {
            methodOnExcept.run();
            tryUntilNoError(runMethod, methodOnExcept);
        }
    }

    public static String getText(String errorMessage) {
        return PREFIX + errorMessage;
    }
}
