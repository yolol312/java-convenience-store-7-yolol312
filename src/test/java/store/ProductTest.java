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
    void 상품은_수량을_차감할_수_있다() {
        //given
        final String expectedProduct = "콜라 1,000원 8개 탄산2+1";
        final Product product = new Product("콜라", 1000, 10, "탄산2+1");

        //when
        product.deductQuantity(2);

        //then
        assertThat(product.toString()).isEqualTo(expectedProduct);
    }

    @Test
    void 가지고_있는_수량보다_더_차감_되면_예외가_발생한다() {
        //given
        String expectedMessage = "[ERROR] 해당 상품의 재고가 부족합니다.";
        final Product product = new Product("콜라", 1000, 10, "탄산2+1");

        //when & then
        assertThatThrownBy(() -> product.deductQuantity(11))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(expectedMessage);
    }
}