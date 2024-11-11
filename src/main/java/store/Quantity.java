package store;

import java.util.Objects;
import java.util.stream.Stream;

public class Quantity {
    private int quantity;

    public Quantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice(final int price) {
        return quantity * price;
    }

    public int getQuantity() {
        return quantity;
    }

    public boolean isQuantityAtLeast(final Product otherProduct) {
        return quantity >= otherProduct.getQuantity();
    }

    public boolean hasRemainingQuantity() {
        return this.quantity > 0;
    }

    public boolean canOrder(final Product regularStockProduct, final Product promotionStockProduct) {
        return this.quantity <= getTotalQuantity(regularStockProduct, promotionStockProduct);
    }

    public void deductQuantity(final Product otherProduct) {
        this.quantity -= otherProduct.getQuantity();
    }

    private int getTotalQuantity(final Product regularStockProduct, final Product promotionStockProduct) {
        return Stream.of(regularStockProduct, promotionStockProduct)
                .filter(Objects::nonNull)
                .mapToInt(Product::getQuantity)
                .sum();
    }

    @Override
    public String toString() {
        return String.valueOf(quantity);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Quantity otherQuantity = (Quantity) o;
        return Objects.equals(quantity, otherQuantity.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity);
    }
}
