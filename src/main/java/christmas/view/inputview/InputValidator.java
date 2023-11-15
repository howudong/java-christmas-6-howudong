package christmas.view.inputview;

import christmas.util.ErrorManager;

public final class InputValidator {
    private static final String ONLY_KOREAN_WORD = "[^가-힣]";

    public void isNumeric(String input) {
        try {
            Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ErrorManager.getText(ErrorManager.INVALID_DATE));
        }
    }

    public void hasExactlyContains(char target, int count, String input) {
        int containSize = (int) input.chars().filter(e -> e == target).count();
        if (containSize != count) {
            throw new IllegalArgumentException(ErrorManager.getText(ErrorManager.INVALID_ORDER));
        }
    }

    public void isOnlyWords(String input) {
        boolean hasContainNotKoreanWord = input.chars()
                .anyMatch(e -> String.valueOf((char) e).matches(ONLY_KOREAN_WORD));

        if (hasContainNotKoreanWord) {
            throw new IllegalArgumentException(ErrorManager.getText(ErrorManager.INVALID_ORDER));
        }
    }
}
