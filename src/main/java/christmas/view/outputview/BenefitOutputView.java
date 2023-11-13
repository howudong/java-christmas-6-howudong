package christmas.view.outputview;

import christmas.domain.BadgeType;
import christmas.domain.Product;
import christmas.dto.BenefitDto;
import christmas.dto.DiscountDto;
import christmas.dto.InputDto;
import christmas.dto.OutputDto;

import java.util.Map;

public final class BenefitOutputView implements OutputView {

    private static final String EMPTY_TEXT = "없음";

    @Override
    public void view(Map<String, InputDto> inputs, Map<String, OutputDto> outputs) {
        BenefitDto benefitDto = assignDto(BenefitDto.class, outputs, "benefitDto");
        DiscountDto discountDto = assignDto(DiscountDto.class, outputs, "discountDto");

        if (benefitDto != null && discountDto != null) {
            viewRewardProduct(benefitDto);
            viewDiscounts(discountDto);
            viewBenefitPrice(benefitDto);
            viewTotalDiscountPrice(discountDto);
            viewRewardBadge(benefitDto);
        }
    }

    private <T extends OutputDto> T assignDto(Class<T> Target, Map<String, OutputDto> outputs, String name) {
        if (outputs.containsKey(name)) {
            return Target.cast(outputs.get(name));
        }
        return null;
    }

    private void viewRewardProduct(BenefitDto dto) {
        System.out.println("<증정 메뉴>");
        Product product = dto.rewardProduct();
        if (product == null) {
            System.out.println(EMPTY_TEXT);
            return;
        }
        System.out.println(product.getName() + " " + product.getPrice() + "개\n");
    }

    private void viewDiscounts(DiscountDto dto) {
        System.out.println("<혜택 내역>");
        Map<String, Long> discounts = dto.getDiscounts();
        if (discounts.isEmpty()) {
            System.out.println(EMPTY_TEXT + "\n");
            return;
        }
        discounts.forEach((key, value) -> System.out.println(key + " " + convertDiscountPay(value)));
        System.out.println();
    }

    private void viewBenefitPrice(BenefitDto dto) {
        System.out.println("<총혜택 금액>");
        Long benefitPrice = dto.getBenefitPrice();
        System.out.println(convertDiscountPay(benefitPrice));
        System.out.println();
    }

    private void viewRewardBadge(BenefitDto dto) {
        BadgeType badge = dto.badgeType();

        System.out.println("<12월 이벤트 배지>");
        if (badge == null) {
            System.out.println(EMPTY_TEXT);
            return;
        }
        System.out.println(badge.getBadgeName());
    }

    private void viewTotalDiscountPrice(DiscountDto dto) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(convertToPay(dto.getTotalDiscountPrice()));
    }

    private String convertDiscountPay(Long pay) {
        return String.format("-%,d원", pay);
    }

    private String convertToPay(Long pay) {
        return String.format("-%,d원", pay);
    }
}
