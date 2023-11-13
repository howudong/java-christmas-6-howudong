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
            printWithLineBreaking(EMPTY_TEXT);
            return;
        }
        // TODO 증정메뉴 반환할때 (상품 이름, 개수) 쌍으로 받도록 하기.
        String print = product.getName() + " " + "1개";
        printWithLineBreaking(print);
    }

    private void viewDiscounts(DiscountDto dto) {
        System.out.println("<혜택 내역>");
        Map<String, Long> discounts = dto.getDiscounts();
        if (discounts.isEmpty()) {
            printWithLineBreaking(EMPTY_TEXT);
            return;
        }
        discounts.forEach((key, value) -> System.out.println(key + ": " + convertDiscountPay(value)));
        System.out.println();
    }

    private void viewBenefitPrice(BenefitDto dto) {
        System.out.println("<총혜택 금액>");
        Long benefitPrice = dto.getBenefitPrice();
        if (benefitPrice <= 0L) {
            printWithLineBreaking(convertToPay(benefitPrice));
            return;
        }
        printWithLineBreaking(convertDiscountPay(benefitPrice));
    }

    private void printWithLineBreaking(String benefitPrice) {
        System.out.println(benefitPrice);
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
        String formattedPrice = convertToPay(dto.getTotalDiscountPrice());
        printWithLineBreaking(formattedPrice);
    }

    private String convertDiscountPay(Long pay) {
        return String.format("-%,d원", pay);
    }

    private String convertToPay(Long pay) {
        return String.format("%,d원", pay);
    }
}
