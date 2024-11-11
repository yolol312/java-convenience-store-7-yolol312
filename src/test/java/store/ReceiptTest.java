package store;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReceiptTest {
    @Test
    void 영수증을_발행할_수_있다() {
        //given
        OrderedProduct cola = new OrderedProduct("콜라", 3000, 3);
        OrderedProduct energyBar = new OrderedProduct("오렌지주스", 3600, 2);
        List<OrderedProduct> orderedProducts = Arrays.asList(cola, energyBar);

        Map<OrderedProduct, Integer> promotionProducts = new HashMap<>();
        promotionProducts.put(cola, 1);
        promotionProducts.put(energyBar, 1);

        Receipt receipt = new Receipt(orderedProducts);

        String expectedResult = "==============W 편의점================\n"
                + "상품명\t\t수량\t금액\n"
                + "콜라\t\t3\t3,000\n"
                + "오렌지주스\t\t2\t3,600\n"
                + "=============증\t정===============\n"
                + "콜라\t\t1\n"
                + "오렌지주스\t\t1\n"
                + "====================================\n"
                + "총구매액\t\t5\t6,600\n"
                + "행사할인\t\t\t-2,800\n"
                + "멤버십할인\t\t\t-0\n"
                + "내실돈\t\t\t 3,800\n";

        //when
        String result = receipt.createReceipt(promotionProducts);

        //then
        assertThat(result).contains(expectedResult);
    }
}