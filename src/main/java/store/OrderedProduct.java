package store;

import java.util.Objects;

public class OrderedProduct implements Product {
    private final String name;
    private final PaymentAmount paymentAmount;
    private final Quantity quantity;

    public OrderedProduct(final String name, final int paymentAmount, final int quantity) {
        validateOrderQuantity(quantity);
        this.name = name;
        this.paymentAmount = new PaymentAmount(paymentAmount);
        this.quantity = new Quantity(quantity);
    }

    public boolean hasRemainingQuantity() {
        return quantity.hasRemainingQuantity();
    }

    public boolean canOrder(final Product regularStockProduct, final Product promotionStockProduct) {
        return quantity.canOrder(regularStockProduct, promotionStockProduct);
    }

    public void discountPaymentAmount(Membership membership) {
        paymentAmount.discountPaymentAmount(membership);
    }

    private void validateOrderQuantity(final int quantity) {
        if (isNegative(quantity)) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 양수여야 합니다. 다시 입력해 주세요.");
        }
    }

    private boolean isNegative(final int quantity) {
        return quantity <= 0;
    }

    public int getPaymentAmount() {
        return paymentAmount.getPaymentAmount();
    }

    @Override
    public void deductQuantity(final Product otherProduct) {
        quantity.deductQuantity(otherProduct);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getQuantity() {
        return quantity.getQuantity();
    }

    @Override
    public String toString() {
        return String.join(" ", name, quantity + "");
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product)) {
            return false;
        }
        final Product otherProduct = (Product) obj;
        return Objects.equals(this.name, otherProduct.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
