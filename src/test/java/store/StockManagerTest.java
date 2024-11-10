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
    void 재고품에_존재하지_않는_상품을_구매하면_예외가_발생한다() {
        //given
        final String expectedMessage = "[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.";
        final OrderedProduct orderedProduct = new OrderedProduct("안성모", 1);

        //when & then
        assertThatThrownBy(() -> stockManager.canProceedWithPayment(orderedProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}