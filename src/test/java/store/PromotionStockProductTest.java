package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionStockProductTest {
    @Test
    void 프로모션_재고_상품은_상품명_수량_가격_프로모션을_가진다() {
        //given
        final String expectedProduct = "콜라 1,000원 10개 탄산2+1";

        //when
        final PromotionStockProduct promotionStockProduct = new PromotionStockProduct("콜라", 1000, 10, "탄산2+1");

        //then
        assertThat(promotionStockProduct.toString()).isEqualTo(expectedProduct);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 2, '콜라 1,000원 8개 탄산2+1', false",
            "콜라, 10, '콜라 1,000원 재고 없음 탄산2+1', false",
            "콜라, 15, '콜라 1,000원 재고 없음 탄산2+1', true"
    })
    void 프로모션_재고_상품의_수량만큼만_구매할_수_있다(final String productName,
                                    final int orderQuantity,
                                    final String expectedStock,
                                    final boolean expectedOrderRemaining) {
        //given
        final PromotionStockProduct promotionStockProduct = new PromotionStockProduct(productName, 1000, 10, "탄산2+1");
        final OrderedProduct orderedProduct = new OrderedProduct(productName, orderQuantity);

        //when
        promotionStockProduct.deductQuantity(orderedProduct);

        //then
        assertThat(promotionStockProduct.toString()).isEqualTo(expectedStock);
        assertThat(orderedProduct.hasRemainingQuantity()).isEqualTo(expectedOrderRemaining);
    }
}