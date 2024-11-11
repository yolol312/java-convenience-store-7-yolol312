package store.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import store.domain.Receipt;
import store.domain.Separator;
import store.domain.StockManager;
import store.domain.membership.Membership;
import store.domain.membership.MembershipManager;
import store.domain.product.OrderedProduct;
import store.domain.product.PromotionStockProduct;
import store.domain.promotion.Promotion;
import store.domain.promotion.PromotionManager;

public class ConvenienceStoreService {
    private final Separator separator;
    private final StockManager stockManager;
    private final PromotionManager promotionManager;
    private final MembershipManager membershipManager;
    private Receipt receipt;

    public ConvenienceStoreService(Separator separator,
                                   StockManager stockManager,
                                   PromotionManager promotionManager,
                                   MembershipManager membershipManager) {
        this.separator = separator;
        this.stockManager = stockManager;
        this.promotionManager = promotionManager;
        this.membershipManager = membershipManager;
    }

    public String getStockStatus() {
        return stockManager.getStockStatus();
    }

    public List<OrderedProduct> getOrderedProducts(String inputOrderedProducts) {
        Map<String, String> productQuantities = separator.getOrderedProduct(inputOrderedProducts);
        List<OrderedProduct> initOrderedProduct = createInitOrderedProducts(productQuantities);
        validateProceedWithPayment(initOrderedProduct);
        List<OrderedProduct> orderedProducts = prepareOrderedProducts(initOrderedProduct);
        receipt = new Receipt(orderedProducts);
        return receipt.getOrderedProducts();
    }

    public void applyMembership(String answer, List<OrderedProduct> orderedProducts) {
        List<OrderedProduct> orderedProductsWithoutPromotion =
                createOrderedProductWithoutPromotion(orderedProducts);
        for (OrderedProduct orderedProduct : orderedProductsWithoutPromotion) {
            if (Objects.equals(answer, "Y")) {
                membershipManager.applyMemberDiscount(orderedProduct, Membership.MEMBER);
                return;
            }
            membershipManager.applyMemberDiscount(orderedProduct, Membership.NON_MEMBER);
        }
    }

    public String getReceipt(List<OrderedProduct> orderedProducts) {
        Map<OrderedProduct, Integer> orderedProductPromotions =
                createOrderedProductPromotions(orderedProducts);
        Map<OrderedProduct, Integer> freebies = getFreebies(orderedProductPromotions);
        return receipt.createReceipt(freebies);
    }

    public boolean applyRetry(String answer) {
        return Objects.equals(answer, "Y");
    }

    public Map<Boolean, Map<String, Integer>> getPromotionMapping(
            List<OrderedProduct> orderedProducts) {
        Map<OrderedProduct, Integer> orderedProductQuantities = createOrderedProductPromotions(orderedProducts);
        Map<String, Integer> canApplyPromotion = new HashMap<>();
        Map<String, Integer> cannotApplyPromotion = new HashMap<>();
        for (Map.Entry<OrderedProduct, Integer> entry : orderedProductQuantities.entrySet()) {
            processPromotion(entry, canApplyPromotion, cannotApplyPromotion);
        }
        return createResultMap(canApplyPromotion, cannotApplyPromotion);
    }

    public void applyPromotionExclusion(String answer, List<OrderedProduct> orderedProducts,
                                        Map.Entry<String, Integer> orderedProductPromotion) {
        if (Objects.equals(answer, "N")) {
            orderedProducts.stream()
                    .filter(product -> product.equalsName(orderedProductPromotion.getKey()))
                    .forEach(product -> product.deductQuantity(orderedProductPromotion.getValue()));
        }
    }

    public void applyPromotion(String answer, List<OrderedProduct> orderedProducts,
                               Map.Entry<String, Integer> orderedProductPromotion) {
        if (Objects.equals(answer, "Y")) {
            orderedProducts.stream()
                    .filter(product -> product.equalsName(orderedProductPromotion.getKey()))
                    .forEach(product -> product.increaseQuantity(orderedProductPromotion.getValue()));
        }
    }

    private List<OrderedProduct> createInitOrderedProducts(Map<String, String> orderedProducts) {
        List<OrderedProduct> orderedProductList = new ArrayList<>();
        for (Map.Entry<String, String> entry : orderedProducts.entrySet()) {
            String productName = entry.getKey();
            String quantity = entry.getValue();
            orderedProductList.add(new OrderedProduct(productName, 0, quantity));
        }
        return orderedProductList;
    }

    private List<OrderedProduct> prepareOrderedProducts(List<OrderedProduct> initOrderedProduct) {
        List<OrderedProduct> orderedProducts = new ArrayList<>();
        for (OrderedProduct orderedProduct : initOrderedProduct) {
            orderedProducts.add(stockManager.prepareOrderedProduct(orderedProduct));
        }
        return orderedProducts;
    }

    private void validateProceedWithPayment(List<OrderedProduct> initOrderedProduct) {
        for (OrderedProduct orderedProduct : initOrderedProduct) {
            stockManager.validateProceedWithPayment(orderedProduct);
        }
    }

    private Map<OrderedProduct, Integer> createOrderedProductPromotions(List<OrderedProduct> orderedProducts) {
        Map<OrderedProduct, Integer> orderedProductPromotions = new HashMap<>();
        for (OrderedProduct orderedProduct : orderedProducts) {
            PromotionStockProduct product = findPromotionStockProduct(orderedProduct);
            if (product != null && calculateQuantity(orderedProduct, product) > 0) {
                orderedProductPromotions.put(orderedProduct, calculateQuantity(orderedProduct, product));
            }
        }
        return orderedProductPromotions;
    }

    private Integer calculateQuantity(OrderedProduct orderedProduct, PromotionStockProduct product) {
        Promotion promotion = promotionManager.getMatchingPromotion(product);
        return calculateRequiredQuantityForPromotion(orderedProduct, promotion);
    }

    private List<OrderedProduct> createOrderedProductWithoutPromotion(List<OrderedProduct> orderedProducts) {
        List<OrderedProduct> productsWithoutPromotion = new ArrayList<>();
        for (OrderedProduct orderedProduct : orderedProducts) {
            PromotionStockProduct product = findPromotionStockProduct(orderedProduct);
            if (product == null) {
                productsWithoutPromotion.add(orderedProduct);
            }
        }
        return productsWithoutPromotion;
    }

    private PromotionStockProduct findPromotionStockProduct(OrderedProduct orderedProduct) {
        return (PromotionStockProduct) stockManager.findPromotionStockProduct(orderedProduct);
    }

    private Integer calculateRequiredQuantityForPromotion(OrderedProduct orderedProduct, Promotion promotion) {
        return promotionManager.calculateRequiredQuantityForPromotion(orderedProduct, promotion);
    }

    private void processPromotion(Map.Entry<OrderedProduct, Integer> entry, Map<String, Integer> canApplyPromotion,
                                  Map<String, Integer> cannotApplyPromotion) {
        OrderedProduct orderedProduct = entry.getKey();
        Integer quantity = entry.getValue();
        PromotionStockProduct promotionStockProduct =
                (PromotionStockProduct) stockManager.findPromotionStockProduct(orderedProduct);
        if (promotionStockProduct.isOrderable(quantity)) {
            canApplyPromotion.put(orderedProduct.getName(), quantity);
            return;
        }
        cannotApplyPromotion.put(orderedProduct.getName(), quantity);
    }

    private Map<Boolean, Map<String, Integer>> createResultMap(Map<String, Integer> canApplyPromotion,
                                                               Map<String, Integer> cannotApplyPromotion) {
        Map<Boolean, Map<String, Integer>> result = new HashMap<>();
        result.put(true, canApplyPromotion);
        result.put(false, cannotApplyPromotion);
        return result;
    }

    private Map<OrderedProduct, Integer> getFreebies(Map<OrderedProduct, Integer> orderedProductPromotions) {
        Map<OrderedProduct, Integer> freebies = new HashMap<>();
        for (OrderedProduct orderedProduct : orderedProductPromotions.keySet()) {
            PromotionStockProduct promotionStockProduct =
                    (PromotionStockProduct) stockManager.findPromotionStockProduct(orderedProduct);
            Promotion promotion = promotionManager.getMatchingPromotion(promotionStockProduct);
            Integer freebie = promotionManager.calculateFreebies(orderedProduct, promotion);
            freebies.put(orderedProduct, freebie);
        }
        return freebies;
    }
}
