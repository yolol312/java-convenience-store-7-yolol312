package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import store.domain.product.OrderedProduct;
import store.domain.product.RegularStockProduct;

class RegularStockProductTest {
    @Test
    void 일반_재고_상품은_상품명_수량_가격을_가진다() {
        //given
        final String expectedProduct = "콜라 1,000원 10개";

        //when
        final RegularStockProduct regularStockProduct = new RegularStockProduct("콜라", 1000, 10);

        //then
        assertThat(regularStockProduct.toString()).isEqualTo(expectedProduct);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 2, 2000, '콜라 1,000원 8개'",
            "콜라, 10, 10000, '콜라 1,000원 재고 없음'"
    })
    void 일반_재고_상품은_구매_수량_만큼_재고_수량을_차감할_수_있다(final String productName,
                                            final int orderQuantity,
                                            final int paymentAmount,
                                            String expectedStock) {
        //given
        final RegularStockProduct regularStockProduct = new RegularStockProduct(productName, 1000, 10);
        final OrderedProduct orderedProduct = new OrderedProduct(productName, paymentAmount,
                String.valueOf(orderQuantity));

        //when
        regularStockProduct.deductQuantity(orderedProduct);

        //then
        assertThat(regularStockProduct.toString()).isEqualTo(expectedStock);
    }

    @Test
    void 일반_재고_상품의_재고_수량을_초과하여_구매하면_예외가_발생한다() {
        //given
        final String expectedMessage = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
        final RegularStockProduct regularStockProduct = new RegularStockProduct("콜라", 1000, 10);
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 11000, String.valueOf(11));

        //when & then
        assertThatThrownBy(() -> regularStockProduct.deductQuantity(orderedProduct))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}