package store;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class PromotionStockProduct implements Product {
    private final String name;
    private final int price;
    private final String promotion;
    private int quantity;

    public PromotionStockProduct(final String name, final int price, final String promotion, final int quantity) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.quantity = quantity;
    }

    public String getPromotion() {
        return promotion;
    }

    @Override
    public void deductQuantity(final Product otherProduct) {
        int amountToDeduct = Math.min(this.quantity, otherProduct.getQuantity());
        this.quantity -= amountToDeduct;
        otherProduct.deductQuantity(new OrderedProduct(this.name, amountToDeduct));
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
        return Objects.equals(this.name, otherProduct.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    private String getProductInfo() {
        return String.join(" ", name, formatPrice(price), formatQuantity(quantity), promotion);
    }

    private String formatPrice(final int price) {
        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price) + "원";
    }

    private String formatQuantity(final int quantity) {
        if (quantity == 0) {
            return "재고 없음";
        }
        return quantity + "개";
    }
}
