package store;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class ProductTest {
    @Test
    void 상품은_이름_가격_수량_프로모션을_가진다() {
        //given
        final String expectedProduct = "콜라 1,000원 10개 탄산2+1";

        //when
        final Product product = new Product("콜라", 1000, 10, "탄산2+1");

        //then
        assertThat(product.toString()).isEqualTo(expectedProduct);
    }

    @Test
    void 상품은_구매_수량_만큼_재고_수량을_차감할_수_있다() {
        //given
        final String expectedProduct = "콜라 1,000원 8개 탄산2+1";
        final Product product = new Product("콜라", 1000, 10, "탄산2+1");

        //when
        product.deductQuantity(2);

        //then
        assertThat(product.toString()).isEqualTo(expectedProduct);
    }

    @Test
    void 재고_수량을_초과하여_구매하면_예외가_발생한다() {
        //given
        final String expectedMessage = "[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.";
        final Product product = new Product("콜라", 1000, 10, "탄산2+1");

        //when & then
        assertThatThrownBy(() -> product.deductQuantity(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}