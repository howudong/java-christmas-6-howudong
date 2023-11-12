package christmas.view.inputview;

import christmas.dto.InputDto;

import java.util.Map;

public interface InputView {
    void read(Map<InputDto, String> inputs);
}
