package store;

import java.text.NumberFormat;
import java.util.Locale;

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

    public void deductQuantity (int quantity) {
        validateQuantity(quantity);
        this.quantity -= quantity;
    }

    @Override
    public String toString() {
        return name + " " + formatPrice(price) + "원 " + quantity + "개 " + promotion;
    }

    private String formatPrice(int price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price);
    }

    private void validateQuantity (int quantity) {
        if(isQuantityLessThan(quantity)) {
            throw new IllegalArgumentException("[ERROR] 해당 상품의 재고가 부족합니다."); //어떤 상품인지 알려주면 좋을 것 같다
        }
    }

    private boolean isQuantityLessThan(int quantity) {
        return this.quantity < quantity;
    }
}
