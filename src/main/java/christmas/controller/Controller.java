package christmas.controller;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;

import java.util.Map;

public interface Controller {
    void process(Map<String, InputDto> inputs, Map<String, OutputDto> outputs);
}
