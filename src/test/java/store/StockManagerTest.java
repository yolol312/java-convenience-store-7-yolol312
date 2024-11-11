package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.StockManager;
import store.domain.product.OrderedProduct;
import store.domain.product.Product;
import store.io.ProductSupplier;

class StockManagerTest {
    private static StockManager stockManager;

    @BeforeEach
    void setUp() throws IOException {
        final ProductSupplier productSupplier = new ProductSupplier();
        final List<Product> products = productSupplier.supplyProducts();
        stockManager = new StockManager(products);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 10, 10000, true",
            "콜라, 21, 21000, false",
            "정식도시락, 8, 51200, true",
            "정식도시락, 9, 57600, false"
    })

    @Test
    void 재고에_존재하지_않는_상품을_구매하면_예외가_발생한다() {
        //given
        final String expectedMessage = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
        final OrderedProduct orderedProduct = new OrderedProduct("안성모", Integer.MAX_VALUE, String.valueOf(1));

        //when & then
        assertThatThrownBy(() -> stockManager.validateProceedWithPayment(orderedProduct))
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

    @Test
    void 결제된_수량만큼_재고에_있는_해당_상품의_수량을_차감할_수_있다() {
        //given
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 12000, String.valueOf(12));

        //when
        stockManager.deductStockForOrder(orderedProduct);
        String stockStatus = stockManager.getStockStatus();

        //then
        assertThat(stockStatus).isEqualTo(
                "- 콜라 1,000원 재고 없음 탄산2+1\n" +
                        "- 콜라 1,000원 8개\n" +
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