package store;

public class OrderedProduct implements Product {
    private final String name;
    private int quantity;

    public OrderedProduct(final String name, final int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public boolean hasRemainingQuantity() {
        return this.quantity > 0;
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
