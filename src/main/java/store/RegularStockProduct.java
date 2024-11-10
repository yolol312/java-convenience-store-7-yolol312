package store;

import java.text.NumberFormat;
import java.util.Locale;

public class RegularStockProduct implements Product {
    private final String name;
    private final int price;
    private int quantity;

    public RegularStockProduct(final String name, final int price, final int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public void deductQuantity(final Product otherProduct) {
        final int quantity = otherProduct.getQuantity();
        validateQuantity(quantity);
        this.quantity -= quantity;
        otherProduct.deductQuantity(new OrderedProduct(name, quantity));
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
        return quantity;
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
        return this.name.equals(otherProduct.getName());
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    private String formatPrice(final int price) {
        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price) + "원";
    }

    private void validateQuantity(final int quantity) {
        if (isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요.");
        }
    }

    private boolean isQuantityLessThan(final int quantity) {
        return this.quantity < quantity;
    }

    private String getProductInfo() {
        return String.join(" ", name, formatPrice(price), formatQuantity(quantity));
    }

    private String formatQuantity(final int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }
}
