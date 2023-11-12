package christmas.view;

public final class ErrorHandlerView {
    private ErrorHandlerView() {
    }

    public static void view(String errorMessage) {
        System.out.println(errorMessage);
    }
}
