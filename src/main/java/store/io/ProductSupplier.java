package store.io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import store.domain.product.Product;
import store.domain.product.PromotionStockProduct;
import store.domain.product.RegularStockProduct;

public class ProductSupplier {
    private static final String PRODUCTS_PATH = "products.md";

    private static final int COLUMN_HEADER = 1;
    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_PRICE = 1;
    private static final int COLUMN_QUANTITY = 2;
    private static final int COLUMN_PROMOTION = 3;
    private static final String COLUMN_SEPARATOR = ",";

    public List<Product> supplyProducts() throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(PRODUCTS_PATH);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines()
                    .skip(COLUMN_HEADER)
                    .map(this::split)
                    .map(this::convertLineToProduct)
                    .toList();
        }
    }

    private String[] split(String line) {
        return line.split(COLUMN_SEPARATOR);
    }

    private Product convertLineToProduct(String[] data) {
        String name = data[COLUMN_NAME].trim();
        int price = Integer.parseInt(data[COLUMN_PRICE].trim());
        int quantity = Integer.parseInt(data[COLUMN_QUANTITY].trim());
        String promotion = data[COLUMN_PROMOTION].trim();
        return createProduct(name, price, quantity, promotion);
    }

    private Product createProduct(String name, int price, int quantity, String promotion) {
        if (promotion.equals("null")) {
            return new RegularStockProduct(name, price, quantity);
        }
        return new PromotionStockProduct(name, price, promotion, quantity);
    }
}
