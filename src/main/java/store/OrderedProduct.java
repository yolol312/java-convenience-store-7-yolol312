package store;

public class OrderedProduct implements Product {
    private final String name;
    private int quantity;

    public OrderedProduct(final String name, final int quantity) {
        validateOrderQuantity(quantity);
        this.name = name;
        this.quantity = quantity;
    }

    public boolean hasRemainingQuantity() {
        return this.quantity > 0;
    }

    public boolean canOrder(final PromotionStockProduct promotionStockProduct,
                            final RegularStockProduct regularStockProduct) {
        if (promotionStockProduct == null) {
            return this.quantity <= regularStockProduct.getQuantity();
        }
        return this.quantity <= promotionStockProduct.getQuantity() + regularStockProduct.getQuantity();
    }

    private void validateOrderQuantity(final int quantity) {
        if (isNegative(quantity)) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 양수여야 합니다. 다시 입력해 주세요.");
        }
    }

    private boolean isNegative(final int quantity) {
        return quantity <= 0;
    }

    @Override
    public void deductQuantity(final Product otherProduct) {
        this.quantity -= otherProduct.getQuantity();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return String.join(" ", name, quantity + "개");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        final Product other = (Product) obj;
        return this.name.equals(other.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
