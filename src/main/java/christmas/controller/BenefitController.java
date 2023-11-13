package christmas.controller;

import christmas.dto.InputDto;
import christmas.dto.OutputDto;
import christmas.service.BenefitService;
import christmas.service.DiscountService;
import christmas.view.outputview.OutputView;

import java.util.Map;

public final class BenefitController implements Controller {
    private final BenefitService benefitService;
    private final DiscountService discountService;
    private final OutputView outputView;

    public BenefitController(BenefitService benefitService, DiscountService discountService, OutputView outputView) {
        this.benefitService = benefitService;
        this.discountService = discountService;
        this.outputView = outputView;
    }

    @Override
    public void process(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {

    }
}
