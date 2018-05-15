package com.mstawowiak.market.checkout.domain.checkout;

import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import org.springframework.stereotype.Component;

import static com.google.common.base.Preconditions.checkArgument;

@Component
public class CheckoutFactory {

    public Checkout create(CheckoutNo checkoutNo, ReceiptPrinter receiptPrinter) {
        checkArgument(checkoutNo != null, "checkoutNo cannot be null");
        checkArgument(receiptPrinter != null, "receiptPrinter cannot be null");

        return new Checkout(checkoutNo, receiptPrinter);
    }

}