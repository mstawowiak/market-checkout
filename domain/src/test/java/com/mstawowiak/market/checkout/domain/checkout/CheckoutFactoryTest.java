package com.mstawowiak.market.checkout.domain.checkout;

import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class CheckoutFactoryTest {

    private static final CheckoutNo CHECKOUT_NO_1 = CheckoutNo.of(1);

    private CheckoutFactory checkoutFactory;
    private ReceiptPrinter receiptPrinter;

    @Before
    public void before() {
        checkoutFactory = new CheckoutFactory();
        receiptPrinter = mock(ReceiptPrinter.class);
    }

    @Test
    public void shouldCorrectCreateCheckout() {
        Checkout checkout = checkoutFactory.create(CHECKOUT_NO_1, receiptPrinter);
        assertEquals(CHECKOUT_NO_1, checkout.getCheckoutNo());
    }
}
