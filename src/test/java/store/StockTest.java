package store;

import static org.assertj.core.api.Assertions.assertThat;
import static store.Supplier.PRODUCTS_PATH;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StockTest {
    @ParameterizedTest
    @CsvSource({
            "콜라, 10, true",
            "콜라, 21, false",
            "정식도시락, 8, true",
            "정식도시락, 9, false"
    })
    void 각_상품의_재고_수량을_고려하여_결제_가능_여부를_확인한다(final String productName,
                                          final int orderQuantity,
                                          final boolean expectedOrderRemaining) throws IOException {
        //given
        final Supplier distributor = new Supplier();
        final List<Product> products = distributor.supplyProducts(PRODUCTS_PATH);
        final Stock stock = new Stock(products);
        final OrderedProduct orderedProduct = new OrderedProduct(productName, orderQuantity);

        //when
        final boolean isPaymentAvailable = stock.canProceedWithPayment(orderedProduct);

        //then
        assertThat(isPaymentAvailable).isEqualTo(expectedOrderRemaining);
    }
}