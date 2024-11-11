package store;

import java.io.IOException;
import store.controller.ConvenienceStoreController;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        AppConfig appConfig = null;
        try {
            appConfig = new AppConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ConvenienceStoreController convenienceStoreController =
                appConfig.getConvenienceStoreController();

        while (true) {
            if (!convenienceStoreController.run()) {
                break;
            }
        }
    }
}
