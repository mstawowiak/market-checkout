package com.mstawowiak.market.checkout.domain.checkout;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link CheckoutNo}
 */
public class CheckoutNoTest {

    private static final Integer CORRECT_VALUE = 1;
    private static final Integer NEGATIVE_VALUE = -1;
    private static final Integer ZERO_VALUE = 0;

    @Test
    public void shouldCreateCheckoutNoForCorrectValue() {
        CheckoutNo checkoutNo = CheckoutNo.of(CORRECT_VALUE);

        assertEquals(CORRECT_VALUE, checkoutNo.getCheckoutNo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullValue() {
        CheckoutNo.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNegativeValue() {
        CheckoutNo.of(NEGATIVE_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForZeroValue() {
        CheckoutNo.of(ZERO_VALUE);
    }
}
