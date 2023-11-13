package christmas.view.outputview;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;

import java.util.Map;

public interface OutputView {
    void view(Map<InputDto, String> inputs, Map<String, OutputDto> outputs);
}
