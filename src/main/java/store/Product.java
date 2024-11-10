package store;

public interface Product {
    void deductQuantity(final Product otherProduct);

    String getName();

    int getQuantity();

    boolean equals(final Object obj);

    int hashCode();
}
