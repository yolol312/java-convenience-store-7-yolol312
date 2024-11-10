package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OrderedProductTest {
    @Test
    void 주문_상품은_상품명_수량을_가진다() {
        //given
        final String expectedProduct = "콜라 10개";

        //when
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 10);

        //then
        assertThat(orderedProduct.toString()).isEqualTo(expectedProduct);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 0, false",
            "콜라, 5, true",
    })
    void 주문_상품은_잔여_수량을_확인할_수_있다(final String productName,
                                final int orderQuantity,
                                final boolean expectedOrderRemaining) {
        //given
        final OrderedProduct orderedProduct = new OrderedProduct(productName, orderQuantity);

        //when & then
        assertThat(orderedProduct.hasRemainingQuantity()).isEqualTo(expectedOrderRemaining);
    }
}