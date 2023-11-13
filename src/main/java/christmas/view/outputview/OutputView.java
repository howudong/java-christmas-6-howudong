package christmas.view.outputview;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;

import java.util.Map;

public interface OutputView {
    void view(Map<String, InputDto> inputs, Map<String, OutputDto> outputs);
}
