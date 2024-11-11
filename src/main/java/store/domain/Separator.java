package store.domain;

import java.util.HashMap;
import java.util.Map;

public class Separator {
    private static final String PRODUCT_SEPARATOR = ",";
    private static final String QUANTITY_SEPARATOR = "-";
    private static final int LEFT_BRACKET_INDEX = "[".length();  // 대괄호 시작 인덱스
    private static final int RIGHT_BRACKET_INDEX = "]".length();

    public Map<String, String> getOrderedProduct(String input) {
        Map<String, String> productMap = new HashMap<>();
        for (String product : split(input, PRODUCT_SEPARATOR)) {
            String[] parts = split(product.substring(LEFT_BRACKET_INDEX, product.length() - RIGHT_BRACKET_INDEX),
                    QUANTITY_SEPARATOR);
            productMap.put(parts[0], parts[1]);
        }
        return productMap;
    }

    private String[] split(String input, String separator) {
        return input.split(separator);
    }
}
