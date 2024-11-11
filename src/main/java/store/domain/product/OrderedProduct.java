package store.domain.product;

import java.util.Objects;
import store.domain.membership.Membership;
import store.domain.vo.PaymentAmount;
import store.domain.vo.Quantity;

public class OrderedProduct implements Product {
    private final String name;
    private final PaymentAmount paymentAmount;
    private final Quantity quantity;

    public OrderedProduct(final String name, final int paymentAmount, final String quantity) {
        int orderedQuantity = validateQuantity(quantity);
        validatePositive(orderedQuantity);
        this.name = name;
        this.paymentAmount = new PaymentAmount(paymentAmount);
        this.quantity = new Quantity(orderedQuantity);
    }

    public boolean hasRemainingQuantity() {
        return quantity.hasRemainingQuantity();
    }

    public void validateOrderQuantity(final Product regularStockProduct, final Product promotionStockProduct) {
        if (!quantity.canOrder(regularStockProduct, promotionStockProduct)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    public void discountPaymentAmount(Membership membership) {
        paymentAmount.discountPaymentAmount(membership);
    }

    public int getPaymentAmount() {
        return paymentAmount.getPaymentAmount();
    }

    public int getPrice() {
        return paymentAmount.getPaymentAmount() / quantity.getQuantity();
    }

    public boolean equalsName(final String product) {
        return name.equals(product);
    }

    @Override
    public void deductQuantity(final Product otherProduct) {
        quantity.deductQuantity(otherProduct);
    }

    public void deductQuantity(final Integer otherProductQuantity) {
        quantity.deductQuantity(otherProductQuantity);
    }

    public void increaseQuantity(final Integer otherProductQuantity) {
        quantity.increaseQuantity(otherProductQuantity);
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

    private int validateQuantity(final String quantity) {
        try {
            return Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 숫자여야 합니다. 다시 입력해 주세요.");
        }
    }

    private void validatePositive(final int quantity) {
        if (isNegative(quantity)) {
            throw new IllegalArgumentException("[ERROR] 주문 수량은 양수여야 합니다. 다시 입력해 주세요.");
        }
    }

    private boolean isNegative(final int quantity) {
        return quantity <= 0;
    }
}
