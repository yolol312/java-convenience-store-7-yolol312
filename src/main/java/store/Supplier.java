package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class Supplier {
    public static final String PRODUCT_PATH = "products.md";

    public List<Product> supplyProducts(String filePath) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] data = line.split(",");
                        String name = data[0].trim();
                        int price = Integer.parseInt(data[1].trim());
                        int quantity = Integer.parseInt(data[2].trim());
                        String promotion = data[3].trim();

                        // promotion이 "null"일 경우 null로 설정
                        if (promotion.equals("null")) {
                            promotion = null;
                        }
                        return new Product(name, price, quantity, promotion);
                    })
                    .toList();
        }
    }
}
