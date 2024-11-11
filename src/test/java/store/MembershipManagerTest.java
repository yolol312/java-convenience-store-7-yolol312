package store;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MembershipManagerTest {
    private static MembershipManager membershipManager;

    @BeforeEach
    void setUp() {
        membershipManager = new MembershipManager();
    }

    @ParameterizedTest
    @CsvSource({
            "콜라, 10000, 10, MEMBER, 7000",
            "콜라, 10000, 10, NON_MEMBER, 10000",
            "콜라, 30000, 30, MEMBER, 22000"
    })
    void 멤버십_회원이라면_할인이_가능하다(final String productName,
                            final int paymentAmount,
                            final int orderQuantity,
                            final String membershipType,
                            final int expectedResult) {
        //given
        final OrderedProduct orderedProduct = new OrderedProduct(productName, paymentAmount, orderQuantity);
        final Membership membership = Membership.valueOf(membershipType);
        //when
        membershipManager.applyMemberDiscount(orderedProduct, membership);

        //when & then
        assertThat(orderedProduct.getPaymentAmount()).isEqualTo(expectedResult);
    }
}