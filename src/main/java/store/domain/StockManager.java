package store.domain;

import java.util.List;
import java.util.stream.Collectors;
import store.domain.product.OrderedProduct;
import store.domain.product.Product;
import store.domain.product.PromotionStockProduct;
import store.domain.product.RegularStockProduct;

public class StockManager {
    private final List<Product> stockProducts;

    public StockManager(final List<Product> products) {
        this.stockProducts = products;
    }

    public void validateProceedWithPayment(final OrderedProduct orderedProduct) {
        validateOrderedProductInStock(orderedProduct);
        final RegularStockProduct regularStockProduct =
                (RegularStockProduct) findRegularStockProduct(orderedProduct);
        final PromotionStockProduct promotionStockProduct =
                (PromotionStockProduct) findPromotionStockProduct(orderedProduct);
        orderedProduct.validateOrderQuantity(regularStockProduct, promotionStockProduct);
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

    public OrderedProduct prepareOrderedProduct(final OrderedProduct orderedProduct) {
        Product product = findStockProduct(orderedProduct);
        if (product instanceof PromotionStockProduct promotionStockProduct) {
            return promotionStockProduct.prepareOrderedProduct(orderedProduct);
        }
        RegularStockProduct regularStockProduct = (RegularStockProduct) product;
        return regularStockProduct.prepareOrderedProduct(orderedProduct);
    }

    public Product findPromotionStockProduct(final Product product) {
        return stockProducts.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof PromotionStockProduct)
                .findFirst()
                .orElse(null);
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

    private Product findStockProduct(final Product product) {
        return stockProducts.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .findFirst()
                .orElse(null);
    }

    private Product findRegularStockProduct(final Product product) {
        return stockProducts.stream()
                .filter(stockProduct -> stockProduct.equals(product))
                .filter(stockProduct -> stockProduct instanceof RegularStockProduct)
                .findFirst()
                .orElse(null);
    }
}
