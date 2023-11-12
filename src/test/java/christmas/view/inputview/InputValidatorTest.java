package christmas.view.inputview;

import christmas.util.ErrorHandler;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InputValidatorTest {
    private final InputValidator inputValidator = new InputValidator();

    @Test
    @DisplayName("정수 숫자가 아닌 input이 들어온다면 예외가 발생한다.")
    void 숫자_아닐시_예외() {
        //given
        List<String> param = List.of("1-", "가나", "", "1.1");
        //when,then
        for (String input : param) {
            assertThatThrownBy(() -> inputValidator.isNumeric(input))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorHandler.INVALID_DATE);
        }
    }

    @ParameterizedTest
    @MethodSource("createInvalidDelim")
    @DisplayName("Orders 입력에 구분자'-' 가 하나가 아니라면 예외가 발생한다.")
    void 숫자_아닐시_예외(String input) {
        assertThatThrownBy(() -> inputValidator.hasExactlyContains(',', 1, input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);

    }

    @ParameterizedTest
    @MethodSource("createNotOnlyWord")
    @DisplayName("한글이 낱말이 아닌 다른 것이 있다면 예외가 발생")
    void 문자_아닐시_예외(String input) {
        assertThatThrownBy(() -> inputValidator.isOnlyWords(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(ErrorHandler.INVALID_ORDER);

    }

    @MethodSource
    private static Stream<Arguments> createInvalidDelim() {
        return Stream.of(
                Arguments.of("--"),
                Arguments.of("aa--111"),
                Arguments.of("aaa111"),
                Arguments.of("")
        );
    }

    @MethodSource
    private static Stream<Arguments> createNotOnlyWord() {
        return Stream.of(
                Arguments.of("sasd"),
                Arguments.of(".."),
                Arguments.of("ㅁㄴㅇㅁ"),
                Arguments.of("가나다라ㅁ"),
                Arguments.of("ㅏㅏㅏ맓")
        );
    }
}