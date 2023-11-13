package christmas;

import christmas.controller.ControlConfig;
import christmas.controller.MainController;

public class Application {
    public static void main(String[] args) {
        new MainController(new ControlConfig()).run();
    }
}
