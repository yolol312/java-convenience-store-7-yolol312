package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import store.domain.promotion.Promotion;
import store.io.PromotionSupplier;

class PromotionSupplierTest {
    @Test
    void 프로모션들을_공급한다() throws IOException {
        //given
        final PromotionSupplier promotionSupplier = new PromotionSupplier();
        final List<Promotion> expectedPromotions = List.of(
                new Promotion("탄산2+1", 2, 1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31)),
                new Promotion("MD추천상품", 1, 1,
                        LocalDate.of(2024, 1, 1),
                        LocalDate.of(2024, 12, 31)),
                new Promotion("반짝할인", 1, 1,
                        LocalDate.of(2024, 11, 1),
                        LocalDate.of(2024, 11, 30))
        );

        //when
        final List<Promotion> promotions = promotionSupplier.supplyPromotions();

        //then
        assertThat(promotions).containsExactlyElementsOf(expectedPromotions);
    }
}