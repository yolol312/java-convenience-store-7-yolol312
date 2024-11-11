package store.controller;

import java.util.List;
import java.util.Map;
import store.domain.product.OrderedProduct;
import store.service.ConvenienceStoreService;
import store.view.InputView;
import store.view.OutputView;

public class ConvenienceStoreController {
    private final InputView inputView;
    private final OutputView outputView;
    private final ConvenienceStoreService convenienceStoreService;

    public ConvenienceStoreController(InputView inputView, OutputView outputView,
                                      ConvenienceStoreService convenienceStoreService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.convenienceStoreService = convenienceStoreService;
    }

    public boolean run() {
        outputView.printWelcomeMessage();
        outputView.printStockProducts(convenienceStoreService.getStockStatus());

        String inputOrderedProduct = inputView.getOrderedProduct();
        List<OrderedProduct> orderedProducts = convenienceStoreService.getOrderedProducts(inputOrderedProduct);

        Map<Boolean, Map<String, Integer>> promotionMapping = convenienceStoreService.getPromotionMapping(
                orderedProducts);

        Map<String, Integer> canApplyMap = promotionMapping.get(true);
        Map<String, Integer> cannotApplyMap = promotionMapping.get(false);

        if (canApplyMap != null) {
            for (Map.Entry<String, Integer> productPromotion : canApplyMap.entrySet()) {
                String additionalConfirmationAnswer = inputView.getPromotionAdditionalItemConfirmation(
                        productPromotion.getKey());
                convenienceStoreService.applyPromotion(additionalConfirmationAnswer, orderedProducts, productPromotion);
            }
        }

        if (cannotApplyMap != null) {
            for (Map.Entry<String, Integer> productPromotion : cannotApplyMap.entrySet()) {
                String confirmationAnswer = inputView.getPromotionDiscountConfirmation(productPromotion);
                convenienceStoreService.applyPromotionExclusion(confirmationAnswer, orderedProducts, productPromotion);
            }
        }

        String membershipDiscountAnswer = inputView.getMembershipDiscountAnswer();
        convenienceStoreService.applyMembership(membershipDiscountAnswer, orderedProducts);

        String receipt = convenienceStoreService.getReceipt(orderedProducts);
        outputView.pritnReceipt(receipt);

        String answer = inputView.getRetryAnswer();
        return convenienceStoreService.applyRetry(answer);
    }
}
