package christmas.controller;

import christmas.dto.BenefitDto;
import christmas.dto.DiscountDto;
import christmas.dto.InputDto;
import christmas.dto.OutputDto;
import christmas.service.BenefitService;
import christmas.service.DiscountService;
import christmas.view.Parameter;
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
        initOutputs(outputs);
        outputView.view(inputs, outputs);
    }

    private void initOutputs(Map<String, OutputDto> outputs) {
        BenefitDto benefitDto = benefitService.createBenefitDto();
        DiscountDto discountDto = discountService.createDiscountDto();
        outputs.put(Parameter.Output.BENEFIT_DTO, benefitDto);
        outputs.put(Parameter.Output.DISCOUNT_DTO, discountDto);
    }
}
