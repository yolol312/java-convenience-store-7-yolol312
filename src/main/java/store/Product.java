package store;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

public class Product {
    private final String name;
    private final int price;
    private int quantity;
    private final String promotion;

    public Product(String name, int price, int quantity, String promotion) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public void deductQuantity(int quantity) {
        validateQuantity(quantity);
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return getProductInfo();
    }

    private String formatPrice(int price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price);
    }

    private void validateQuantity(int quantity) {
        if (isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("[ERROR] 재고 수량을 초과하여 구매할 수 없습니다. 다시 입력해 주세요."); //어떤 상품인지 알려주면 좋을 것 같다
        }
    }

    private boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }

    private String getPromotionOrEmpty(String promotion) {
        return Optional.ofNullable(promotion).orElse("");
    }

    private String getProductInfo() {
        if (promotion == null) {
            return String.join(" ", name, formatPrice(price) + "원", quantity + "개");
        }
        return String.join(" ", name, formatPrice(price) + "원", quantity + "개", promotion);
    }
}
