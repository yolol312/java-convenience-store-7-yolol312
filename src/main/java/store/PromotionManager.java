package store;

import camp.nextstep.edu.missionutils.DateTimes;
import java.util.List;

public class PromotionManager {
    private final List<Promotion> promotions;

    public PromotionManager(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion getMatchingPromotion(final PromotionStockProduct product) {
        return promotions.stream()
                .filter(promotion -> promotion.isPromotionMatch(product))
                .findFirst()
                .orElse(null);
    }

    public int calculateRequiredQuantityForPromotion(final OrderedProduct product, final Promotion promotion) {
        final Promotion productPromotion = findPromotion(promotion);
        if (promotion.isPromotionActive(DateTimes.now())) {
            return promotion.getRequiredQuantityForPromotion(product);
        }
        return 0;
    }

    public Integer calculateFreebies(final OrderedProduct product, final Promotion promotion) {
        return promotion.calculateFreebies(product);
    }

    private Promotion findPromotion(final Promotion productPromotion) {
        return promotions.stream()
                .filter(promotion -> promotion.equals(productPromotion))
                .findFirst()
                .orElse(null);
    }
}
