package store.domain.membership;

public enum Membership {
    MEMBER(0.3f, 8_000),
    NON_MEMBER(0.0f, Integer.MAX_VALUE);

    private final float discountRate;
    private final int maxDiscountAmount;

    Membership(final float discountRate, final int maxDiscountAmount) {
        this.discountRate = discountRate;
        this.maxDiscountAmount = maxDiscountAmount;
    }

    public float getDiscountRate() {
        return discountRate;
    }

    public int getMaxDiscountAmount() {
        return maxDiscountAmount;
    }
}
