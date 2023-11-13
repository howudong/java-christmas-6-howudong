package christmas.controller;

import java.util.Map;

public interface Controller {
    void process(Map<String, String> inputs, Map<String, Object> outputs);
}
