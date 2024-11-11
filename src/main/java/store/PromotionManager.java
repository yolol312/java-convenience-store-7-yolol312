package store;

import java.util.List;

public class PromotionManager {
    private final List<Promotion> promotions;

    public PromotionManager(List<Promotion> promotions) {
        this.promotions = promotions;
    }

    public Promotion getMatchingPromotion(PromotionStockProduct product) {
        return promotions.stream()
                .filter(promotion -> promotion.isPromotionMatch(product))
                .findFirst()
                .orElse(null);
    }
}
