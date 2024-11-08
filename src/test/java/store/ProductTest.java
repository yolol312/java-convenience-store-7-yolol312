package store;

import static org.assertj.core.api.Assertions.assertThat;

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
}