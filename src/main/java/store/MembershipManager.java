package store;

public class MembershipManager {

    public void applyMemberDiscount(final OrderedProduct orderedProduct, final Membership membership) {
        orderedProduct.discountPaymentAmount(membership);
    }
}
