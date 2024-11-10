package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PromotionTest {
    private static Promotion promotion;

    @BeforeAll
    static void setUp() {
        final LocalDate startDate = LocalDate.parse("2024-01-01");
        final LocalDate endDate = LocalDate.parse("2024-12-31");
        promotion = new Promotion("탄산2+1", 2, 1, startDate, endDate);
    }

    @ParameterizedTest
    @CsvSource({
            "'2023-12-31T23:59:59', false",
            "'2024-01-01T00:00:00', true",
            "'2024-11-11T02:00:00', true",
            "'2024-12-31T23:59:59', true",
            "'2025-01-01T00:00:00', false"
    })
    void 프로모션_기간인지_확인할_수_있다(final String dateStr, final boolean expectedPromotionActive) {
        // given
        LocalDateTime date = LocalDateTime.parse(dateStr);

        //when
        final boolean isPromotionActive = promotion.isPromotionActive(date);

        //then
        assertThat(isPromotionActive).isEqualTo(expectedPromotionActive);
    }
}