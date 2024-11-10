package store;

import java.util.List;

public class Stock {
    private final List<Product> stock;

    public Stock(final List<Product> products) {
        this.stock = products;
    }

    public boolean canProceedWithPayment(final OrderedProduct orderedProduct) {
        final PromotionStockProduct promotionStockProduct =
                (PromotionStockProduct) findPromotionStockProduct(orderedProduct);
        final RegularStockProduct regularStockProduct =
                (RegularStockProduct) findRegularStockProduct(orderedProduct);
        return orderedProduct.canOrder(promotionStockProduct, regularStockProduct);
    }

    private Product findPromotionStockProduct(final Product product) {
        return stock.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof PromotionStockProduct)
                .findFirst()
                .orElse(null);
    }

    private Product findRegularStockProduct(final Product product) {
        return stock.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof RegularStockProduct)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요."));
    }
}
