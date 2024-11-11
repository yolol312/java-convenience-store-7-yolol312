package store;

import static org.assertj.core.api.Assertions.assertThat;
import static store.PromotionSupplier.PROMOTION_PATH;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionManagerTest {
    private static PromotionManager promotionManager;
    private static Promotion expectedPromotion;

    @BeforeEach
    void setUp() throws IOException {
        final PromotionSupplier promotionSupplier = new PromotionSupplier();
        final List<Promotion> promotions = promotionSupplier.supplyPromotions(PROMOTION_PATH);
        promotionManager = new PromotionManager(promotions);

        final LocalDate startDate = LocalDate.parse("2024-01-01");
        final LocalDate endDate = LocalDate.parse("2024-12-31");
        expectedPromotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 1000, 탄산2+1, 10, true",
            "오렌지주스, 1000, MD추천상품, 10, false",
            "감자칩, 1000, 반짝할인, 10, false",
    })
    void 프로모션_재고_상품에_주어진_프로모션과_일치하는_프로모션을_반환할_수_있다(final String productName,
                                                   final int price,
                                                   final String promotionName,
                                                   final int orderQuantity,
                                                   final boolean expectedPromotionMatch) {
        // given
        final PromotionStockProduct promotionStockProduct =
                new PromotionStockProduct(productName, price, promotionName, orderQuantity);

        //when
        final Promotion promotion = promotionManager.getMatchingPromotion(promotionStockProduct);
        final boolean isPromotionMatched = promotion.equals(expectedPromotion);

        //then
        assertThat(isPromotionMatched).isEqualTo(expectedPromotionMatch);
    }
}