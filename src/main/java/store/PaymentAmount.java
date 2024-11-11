package store;

import java.text.NumberFormat;
import java.util.Locale;

public class PaymentAmount {
    private int paymentAmount;

    public PaymentAmount(int paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void discountPaymentAmount(Membership membership) {
        int discountedPrice = (int) (this.paymentAmount * membership.getDiscountRate());
        if (discountedPrice > membership.getMaxDiscountAmount()) {
            this.paymentAmount -= membership.getMaxDiscountAmount();
        }
        this.paymentAmount -= discountedPrice;
    }

    @Override
    public String toString() {
        return formatPaymentAmount(paymentAmount);
    }

    private String formatPaymentAmount(final int price) {
        final NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        return formatter.format(price);
    }
}
