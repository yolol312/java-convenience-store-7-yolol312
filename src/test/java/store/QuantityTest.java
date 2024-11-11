package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class QuantityTest {
    @Test
    void 상품_가격을_알면_총_가격을_반환할_수_있다() {
        //given
        final Quantity quantity = new Quantity(10);
        final int price = 1000;
        final int expectedPrice = 10000;

        //when
        int totalPrice = quantity.getTotalPrice(price);

        //then
        assertThat(totalPrice).isEqualTo(expectedPrice);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 10, 10000, true",
            "콜라, 11, 11000, false"
    })
    void 주문_상품의_수량_이상인지_확인할_수_있다(final String productName,
                                 final int orderQuantity,
                                 final int paymentAmount,
                                 final boolean expectedResult) {
        //given
        final Quantity quantity = new Quantity(10);
        final OrderedProduct orderedProduct = new OrderedProduct(productName, paymentAmount, orderQuantity);

        //when
        boolean comparisonResult = quantity.isQuantityAtLeast(orderedProduct);

        //then
        assertThat(comparisonResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "0, false",
            "1, true"
    })
    void 수량이_남아있는지_확인할_수_있다(final int inputQuantity, final boolean expectedResult) {
        //given
        final Quantity quantity = new Quantity(inputQuantity);

        //when
        final boolean comparisonResult = quantity.hasRemainingQuantity();

        //then
        assertThat(comparisonResult).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 1000, 탄산2+1, 5, true",
            "사이다, 1000, 탄산2+1, 4, false"
    })
    void 주문이_가능한지_확인할_수_있다(final String productName,
                           final int price,
                           final String promotion,
                           final int quantity,
                           final boolean expectedResult) {
        //given
        final PromotionStockProduct promotionStockProduct =
                new PromotionStockProduct(productName, price, promotion, quantity);
        final RegularStockProduct regularStockProduct =
                new RegularStockProduct(productName, price, quantity);

        final Quantity orderedQuantity = new Quantity(10);

        //when
        final boolean comparisonResult = orderedQuantity.canOrder(promotionStockProduct, regularStockProduct);

        //then
        assertThat(comparisonResult).isEqualTo(expectedResult);
    }

    @Test
    void 수량을_차감할_수_있다() {
        //given
        final Quantity quantity = new Quantity(10);
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 10000, 7);
        final int expectedQuantity = 3;

        //when
        quantity.deductQuantity(orderedProduct);

        //then
        assertThat(quantity.getQuantity()).isEqualTo(expectedQuantity);
    }
}