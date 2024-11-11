package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OrderedProductTest {
    @Test
    void 주문_상품은_상품명_수량을_가진다() {
        //given
        final String expectedProduct = "콜라 10";

        //when
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 10000, 10);

        //then
        assertThat(orderedProduct.toString()).isEqualTo(expectedProduct);
    }

    @Test
    void 주문_상품은_잔여_수량이_있는_지_확인할_수_있다() {
        //given
        final OrderedProduct orderedProduct = new OrderedProduct("콜라", 5000, 5);

        //when & then
        assertThat(orderedProduct.hasRemainingQuantity()).isEqualTo(true);
    }
    //[ERROR] 주문 수량은 양수여야 합니다. 다시 입력해 주세요.

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    void 주문_수량이_양수가_아니라면_예외가_발생한다(int orderQuantity) {
        //given
        final String expectedMessage = "[ERROR] 주문 수량은 양수여야 합니다. 다시 입력해 주세요.";

        //when & then
        assertThatThrownBy(() -> new OrderedProduct("콜라", 0, orderQuantity))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}