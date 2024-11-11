package store.domain.membership;

import store.domain.product.OrderedProduct;

public class MembershipManager {

    public void applyMemberDiscount(final OrderedProduct orderedProduct, final Membership membership) {
        orderedProduct.discountPaymentAmount(membership);
    }
}
