package store.domain.product;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import store.domain.vo.Quantity;

public class RegularStockProduct implements Product {
    private final String name;
    private final int price;
    private final Quantity quantity;

    public RegularStockProduct(final String name, final int price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = new Quantity(quantity);
    }

    public OrderedProduct prepareOrderedProduct(final Product orderedProduct) {
        return new OrderedProduct(orderedProduct.getName(),
                getTotalPrice(orderedProduct),
                String.valueOf(orderedProduct.getQuantity()));
    }

    @Override
    public void deductQuantity(final Product orderedProduct) {
        quantity.deductQuantity(orderedProduct);
        orderedProduct.deductQuantity(orderedProduct);
    }

    @Override
    public String toString() {
        return getProductInfo();
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

    private String formatPrice(final int price) {
        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price) + "원";
    }

    /*
    private void validateQuantity(final Product orderedProduct) {
        if (isQuantityLessThan(orderedProduct)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private boolean isQuantityLessThan(final Product orderedProduct) {
        return !quantity.isQuantityAtLeast(orderedProduct);
    }
     */

    private String getProductInfo() {
        return String.join(" ", name, formatPrice(price), formatQuantity(quantity));
    }

    private String formatQuantity(final Quantity quantity) {
        if (!quantity.hasRemainingQuantity()) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    public int getTotalPrice(final Product orderedProduct) {
        return orderedProduct.getQuantity() * price;
    }
}
