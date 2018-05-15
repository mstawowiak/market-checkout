package com.mstawowiak.market.checkout.domain.checkout;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import static com.google.common.base.Preconditions.checkArgument;
import static lombok.AccessLevel.PRIVATE;

@Value
@RequiredArgsConstructor(access = PRIVATE)
public class CheckoutNo {

    private final Integer checkoutNo;

    public static CheckoutNo of(Integer checkoutNo) {
        checkArgument(checkoutNo != null, "checkoutNo cannot be null");
        checkArgument(checkoutNo > 0, String.format("checkoutNo must be > 0 but is %d", checkoutNo));

        return new CheckoutNo(checkoutNo);
    }

    @Override
    public String toString() {
        return String.valueOf(checkoutNo);
    }
}