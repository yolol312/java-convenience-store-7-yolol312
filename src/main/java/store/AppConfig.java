package store;

import java.io.IOException;
import store.controller.ConvenienceStoreController;
import store.domain.Separator;
import store.domain.StockManager;
import store.domain.membership.MembershipManager;
import store.domain.promotion.PromotionManager;
import store.io.ProductSupplier;
import store.io.PromotionSupplier;
import store.service.ConvenienceStoreService;
import store.view.InputValidator;
import store.view.InputView;
import store.view.OutputView;

public class AppConfig {
    private ProductSupplier productSupplier;
    private PromotionSupplier promotionSupplier;
    private StockManager stockManager;
    private PromotionManager promotionManager;
    private Separator separator;
    private MembershipManager membershipManager;
    private ConvenienceStoreService convenienceStoreService;
    private InputView inputView;
    private OutputView outputView;
    private ConvenienceStoreController convenienceStoreController;

    public AppConfig() throws IOException {
        createProduct();
        createPromotion();
        createStockManager();
        createConvenienceStoreController();
    }

    public ConvenienceStoreController getConvenienceStoreController() {
        return convenienceStoreController;
    }

    private void createProduct() throws IOException {
        this.productSupplier = new ProductSupplier();
        this.stockManager = new StockManager(productSupplier.supplyProducts());
    }

    private void createPromotion() throws IOException {
        this.promotionSupplier = new PromotionSupplier();
        this.promotionManager = new PromotionManager(promotionSupplier.supplyPromotions());
    }

    private void createStockManager() {
        this.separator = new Separator();
        this.membershipManager = new MembershipManager();
        this.convenienceStoreService =
                new ConvenienceStoreService(separator, stockManager, promotionManager, membershipManager);
    }

    private void createConvenienceStoreController() {
        this.inputView = new InputView(new InputValidator());
        this.outputView = new OutputView();
        this.convenienceStoreController =
                new ConvenienceStoreController(inputView, outputView, convenienceStoreService);
    }

}
