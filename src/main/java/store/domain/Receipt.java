package store.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import store.domain.product.OrderedProduct;

public class Receipt {
    private final List<OrderedProduct> orderedProducts;

    public Receipt(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String createReceipt(Map<OrderedProduct, Integer> promotionProducts) {
        StringBuilder receipt = new StringBuilder();
        receipt.append("==============W 편의점================\n");
        receipt.append("상품명\t\t수량\t금액\n");
        for (OrderedProduct product : orderedProducts) {
            receipt.append(String.format("%s\t\t%d\t%,d\n", product.getName(), product.getQuantity(),
                    product.getPaymentAmount()));
        }
        receipt.append("=============증\t정===============\n");
        for (Map.Entry<OrderedProduct, Integer> entry : promotionProducts.entrySet()) {
            OrderedProduct product = entry.getKey();   // OrderedProduct 객체
            Integer quantity = entry.getValue();       // Integer 값 (수량)
            receipt.append(String.format("%s\t\t%d\n", product.getName(), quantity));
        }
        receipt.append("====================================\n");
        receipt.append(String.format("총구매액\t\t%d\t%,d\n", getTotalQuantity(), getTotalPaymentAmount()));
        receipt.append(String.format("행사할인\t\t\t-%,d\n", getPromotionDiscountAmount(promotionProducts)));
        receipt.append(String.format("멤버십할인\t\t\t-%,d\n", getMembershipDiscountAmount(promotionProducts)));
        receipt.append(String.format("내실돈\t\t\t %,d\n", getFinalPaymentAmount(promotionProducts)));
        return receipt.toString();
    }

    public List<OrderedProduct> getOrderedProducts() {
        List<OrderedProduct> deepCopiedList = new ArrayList<>();
        for (OrderedProduct product : orderedProducts) {
            deepCopiedList.add(
                    new OrderedProduct(product.getName(), product.getPrice(), String.valueOf(product.getQuantity())));
        }
        return deepCopiedList;
    }

    private int getTotalQuantity() {
        return orderedProducts.stream()
                .mapToInt(OrderedProduct::getQuantity)
                .sum();
    }

    private int getTotalPaymentAmount() {
        return orderedProducts.stream()
                .mapToInt(OrderedProduct::getPaymentAmount)
                .sum();
    }

    private int getPromotionDiscountAmount(Map<OrderedProduct, Integer> promotionProducts) {
        return orderedProducts.stream()
                .filter(promotionProducts::containsKey)
                .mapToInt(orderedProduct -> orderedProduct.getPrice() * promotionProducts.get(orderedProduct))
                .sum();
    }

    private int getMembershipDiscountAmount(Map<OrderedProduct, Integer> promotionProducts) {
        return getTotalPaymentAmount() - getTotalDiscountPaymentAmount(promotionProducts);
    }

    private int getTotalDiscountPaymentAmount(Map<OrderedProduct, Integer> promotionProducts) {
        return promotionProducts.keySet().stream()
                .mapToInt(OrderedProduct::getPaymentAmount)
                .sum();
    }

    private int getFinalPaymentAmount(Map<OrderedProduct, Integer> promotionProducts) {
        return getTotalPaymentAmount() -
                (getPromotionDiscountAmount(promotionProducts) + getMembershipDiscountAmount(promotionProducts));
    }
}
