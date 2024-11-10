package store;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;

public class PromotionSupplier {
    public static final String PROMOTION_PATH = "promotions.md";

    private static final int COLUMN_HEADER = 1;
    private static final int COLUMN_NAME = 0;
    private static final int COLUMN_BUY = 1;
    private static final int COLUMN_GET = 2;
    private static final int COLUMN_START_DATE = 3;
    private static final int COLUMN_END_DATE = 3;
    private static final String COLUMN_SEPARATOR = ",";

    public List<Promotion> supplyPromotions(String filePath) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {

            return reader.lines()
                    .skip(COLUMN_HEADER)
                    .map(this::split)
                    .map(this::convertLineToPromotion)
                    .toList();
        }
    }

    private String[] split(String line) {
        return line.split(COLUMN_SEPARATOR);
    }

    private Promotion convertLineToPromotion(String[] data) {
        String promotionName = data[COLUMN_NAME].trim();
        int buy = Integer.parseInt(data[COLUMN_BUY].trim());
        int get = Integer.parseInt(data[COLUMN_GET].trim());
        LocalDate startDate = LocalDate.parse(data[COLUMN_START_DATE].trim());
        LocalDate endDate = LocalDate.parse(data[COLUMN_END_DATE].trim());
        return new Promotion(promotionName, buy, get, startDate, endDate);
    }
}
