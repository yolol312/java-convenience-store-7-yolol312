package store;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public boolean isPromotionActive(LocalDateTime date) {
        LocalDate localDate = date.toLocalDate();
        return !localDate.isBefore(startDate) && !localDate.isAfter(endDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Promotion promotion = (Promotion) o;
        return Objects.equals(this.name, promotion.name);  // null 안전하게 비교
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);  // null 안전하게 계산
    }
}