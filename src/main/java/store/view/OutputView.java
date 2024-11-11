package store.view;

public class OutputView {
    public void printWelcomeMessage() {
        System.out.println("안녕하세요. W편의점입니다.");
        System.out.println("현재 보유하고 있는 상품입니다.");
        System.out.println();
    }

    public void printStockProducts(String products) {
        System.out.println(products);
        System.out.println();
    }

    public void pritnReceipt(String receipt) {
        System.out.println(receipt);
        System.out.println();
    }
}
