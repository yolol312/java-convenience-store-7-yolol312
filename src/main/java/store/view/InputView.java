package store.view;

import camp.nextstep.edu.missionutils.Console;
import java.util.Map;

public class InputView {
    private final InputValidator inputValidator;

    public InputView(InputValidator inputValidator) {
        this.inputValidator = inputValidator;
    }

    public String getOrderedProduct() {
        System.out.println("구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])");
        return getInputOrderedProduct();
    }

    public String getPromotionDiscountConfirmation(Map.Entry<String, Integer> promotions) {
        System.out.println(String.format("현재 %s %d개는 프로모션 할인이 적용되지 않습니다. 그래도 구매하시겠습니까? (Y/N)",
                promotions.getKey(),
                promotions.getValue()));
        return getInputAnswer();
    }

    public String getPromotionAdditionalItemConfirmation(String productName) {
        System.out.println(String.format("현재 %s은(는) 1개를 무료로 더 받을 수 있습니다. 추가하시겠습니까? (Y/N)", productName));
        return getInputAnswer();
    }


    public String getMembershipDiscountAnswer() {
        System.out.println("멤버십 할인을 받으시겠습니까? (Y/N)");
        return getInputAnswer();
    }

    public String getRetryAnswer() {
        System.out.println("감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)");
        return getInputAnswer();
    }

    private String getInputOrderedProduct() {
        String input = Console.readLine();
        inputValidator.validateBlank(input);
        inputValidator.validateWhitespace(input);
        return input;
    }

    private String getInputAnswer() {
        String input = Console.readLine();
        inputValidator.validateBlank(input);
        inputValidator.validateWhitespace(input);
        inputValidator.validateYesOrNo(input);
        return input;
    }
}
