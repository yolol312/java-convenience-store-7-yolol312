package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

class SupplierTest {
    private static final String PRODUCTS_PATH = "products.md";

    @Test
    void 상품들을_공급한다() throws IOException {
        //given
        final Supplier distributor = new Supplier();

        //when
        final List<Product> products = distributor.supplyProducts(PRODUCTS_PATH);

        //then
        assertThat(products)
                .extracting(Product::toString)  // Product 객체의 toString 값을 가져옵니다.
                .containsExactlyInAnyOrder(
                        "콜라 1,000원 10개 탄산2+1",
                        "콜라 1,000원 10개",
                        "사이다 1,000원 8개 탄산2+1",
                        "사이다 1,000원 7개",
                        "오렌지주스 1,800원 9개 MD추천상품",
                        "탄산수 1,200원 5개 탄산2+1",
                        "물 500원 10개",
                        "비타민워터 1,500원 6개",
                        "감자칩 1,500원 5개 반짝할인",
                        "감자칩 1,500원 5개",
                        "초코바 1,200원 5개 MD추천상품",
                        "초코바 1,200원 5개",
                        "에너지바 2,000원 5개",
                        "정식도시락 6,400원 8개",
                        "컵라면 1,700원 1개 MD추천상품",
                        "컵라면 1,700원 10개"
                );
    }
}