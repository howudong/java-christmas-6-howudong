package christmas.controller;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;
import christmas.service.BenefitService;
import christmas.view.outputview.OutputView;

import java.util.Map;

public final class BenefitController implements Controller {
    private final BenefitService benefitService;
    private final OutputView outputView;

    public BenefitController(BenefitService benefitService, OutputView outputView) {
        this.benefitService = benefitService;
        this.outputView = outputView;
    }

    @Override
    public void process(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
    }
}
