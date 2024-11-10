package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static store.Supplier.PRODUCTS_PATH;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StockManagerTest {
    private static StockManager stockManager;

    @BeforeAll
    static void setUp() throws IOException {
        final Supplier distributor = new Supplier();
        final List<Product> products = distributor.supplyProducts(PRODUCTS_PATH);
        stockManager = new StockManager(products);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 10, true",
            "콜라, 21, false",
            "정식도시락, 8, true",
            "정식도시락, 9, false"
    })
    void 각_상품의_재고_수량을_고려하여_결제_가능_여부를_확인한다(final String productName,
                                          final int orderQuantity,
                                          final boolean expectedOrderRemaining) {
        //given
        final OrderedProduct orderedProduct = new OrderedProduct(productName, orderQuantity);

        //when
        final boolean isPaymentAvailable = stockManager.canProceedWithPayment(orderedProduct);

        //then
        assertThat(isPaymentAvailable).isEqualTo(expectedOrderRemaining);
    }

    @Test
    void 재고에_존재하지_않는_상품을_구매하면_예외가_발생한다() {
        //given
        final String expectedMessage = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
        final OrderedProduct orderedProduct = new OrderedProduct("안성모", 1);

        //when & then
        assertThatThrownBy(() -> stockManager.canProceedWithPayment(orderedProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }

    @Test
    void 재고_상품들의_정보를_제공한다() {
        //when
        String stockStatus = stockManager.getStockStatus();

        //then
        assertThat(stockStatus).isEqualTo(
                "- 콜라 1,000원 10개 탄산2+1\n" +
                        "- 콜라 1,000원 10개\n" +
                        "- 사이다 1,000원 8개 탄산2+1\n" +
                        "- 사이다 1,000원 7개\n" +
                        "- 오렌지주스 1,800원 9개 MD추천상품\n" +
                        "- 탄산수 1,200원 5개 탄산2+1\n" +
                        "- 물 500원 10개\n" +
                        "- 비타민워터 1,500원 6개\n" +
                        "- 감자칩 1,500원 5개 반짝할인\n" +
                        "- 감자칩 1,500원 5개\n" +
                        "- 초코바 1,200원 5개 MD추천상품\n" +
                        "- 초코바 1,200원 5개\n" +
                        "- 에너지바 2,000원 5개\n" +
                        "- 정식도시락 6,400원 8개\n" +
                        "- 컵라면 1,700원 1개 MD추천상품\n" +
                        "- 컵라면 1,700원 10개"
        );
    }
}