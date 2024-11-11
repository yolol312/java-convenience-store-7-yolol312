package store.domain.promotion;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import store.domain.product.OrderedProduct;
import store.domain.product.PromotionStockProduct;

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
        LocalDate localDate = convertDate(date);
        return !localDate.isBefore(startDate) && !localDate.isAfter(endDate);
    }

    public boolean isPromotionMatch(PromotionStockProduct product) {
        return Objects.equals(name, product.getPromotion());
    }

    public int getRequiredQuantityForPromotion(OrderedProduct product) {
        if (calculateQuantityRemainder(product) == 0) {
            return 0;
        }
        return (buy + get) - calculateQuantityRemainder(product);
    }

    public Integer calculateFreebies(OrderedProduct product) {
        return product.getQuantity() / (buy + get);
    }

    private LocalDate convertDate(LocalDateTime date) {
        return date.toLocalDate();
    }

    private int calculateQuantityRemainder(OrderedProduct product) {
        return product.getQuantity() % (buy + get);  // (buy + get) 만큼 나눈 나머지 계산
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
