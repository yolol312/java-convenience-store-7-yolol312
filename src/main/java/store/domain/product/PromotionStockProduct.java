package store.domain.product;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;
import store.domain.vo.Quantity;

public class PromotionStockProduct implements Product {
    private final String name;
    private final int price;
    private final String promotion;
    private final Quantity quantity;

    public PromotionStockProduct(final String name, final int price, final String promotion, final int quantity) {
        this.name = name;
        this.price = price;
        this.promotion = promotion;
        this.quantity = new Quantity(quantity);
    }

    public String getPromotion() {
        return promotion;
    }

    public OrderedProduct prepareOrderedProduct(final Product orderedProduct) {
        return new OrderedProduct(orderedProduct.getName(),
                getTotalPrice(orderedProduct),
                String.valueOf(orderedProduct.getQuantity()));
    }

    public boolean isOrderable(Integer orderedProductQuantity) {
        return orderedProductQuantity >= quantity.getQuantity();
    }

    @Override
    public void deductQuantity(final Product orderedProduct) {
        if (quantity.isQuantityAtLeast(orderedProduct)) {
            quantity.deductQuantity(orderedProduct);
            orderedProduct.deductQuantity(orderedProduct);
            return;
        }
        orderedProduct.deductQuantity(this);
        quantity.deductQuantity(this);
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

    private String getProductInfo() {
        return String.join(" ", name, formatPrice(price), formatQuantity(quantity), promotion);
    }

    private String formatPrice(final int price) {
        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price) + "원";
    }

    private String formatQuantity(final Quantity quantity) {
        if (!quantity.hasRemainingQuantity()) {
            return "재고 없음";
        }
        return quantity + "개";
    }

    private int getTotalPrice(final Product orderedProduct) {
        return orderedProduct.getQuantity() * price;
    }
}
