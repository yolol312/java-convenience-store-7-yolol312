package store;

import java.util.List;
import java.util.stream.Collectors;

public class StockManager {
    private final List<Product> stockProducts;

    public StockManager(final List<Product> products) {
        this.stockProducts = products;
    }

    public boolean canProceedWithPayment(final OrderedProduct orderedProduct) {
        validateOrderedProductInStock(orderedProduct);
        final RegularStockProduct regularStockProduct =
                (RegularStockProduct) findRegularStockProduct(orderedProduct);
        final PromotionStockProduct promotionStockProduct =
                (PromotionStockProduct) findPromotionStockProduct(orderedProduct);
        return orderedProduct.canOrder(regularStockProduct, promotionStockProduct);
    }

    public String getStockStatus() {
        return stockProducts.stream()
                .map(product -> "- " + product.toString())
                .collect(Collectors.joining("\n"));
    }

    public void deductStockForOrder(final OrderedProduct orderedProduct) {
        final PromotionStockProduct promotionStockProduct =
                (PromotionStockProduct) findPromotionStockProduct(orderedProduct);
        if (promotionStockProduct != null) {
            promotionStockProduct.deductQuantity(orderedProduct);
        }
        if (orderedProduct.hasRemainingQuantity()) {
            final RegularStockProduct regularStockProduct =
                    (RegularStockProduct) findRegularStockProduct(orderedProduct);
            regularStockProduct.deductQuantity(orderedProduct);
        }
    }

    private void validateOrderedProductInStock(final Product product) {
        if (!isOrderedProductInStock(product)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 상품입니다. 다시 입력해 주세요.");
        }
    }

    private boolean isOrderedProductInStock(final Product product) {
        return stockProducts.stream()
                .anyMatch(stockProduct -> stockProduct.equals(product));
    }

    private Product findRegularStockProduct(final Product product) {
        return stockProducts.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof RegularStockProduct)
                .findFirst()
                .orElse(null);
    }

    private Product findPromotionStockProduct(final Product product) {
        return stockProducts.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof PromotionStockProduct)
                .findFirst()
                .orElse(null);
    }
}
