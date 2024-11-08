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
        if(this.quantity < quantity){
            throw new IllegalArgumentException("[ERROR] 해당 상품의 재고가 부족합니다.");
        }
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
}
