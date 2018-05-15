package com.mstawowiak.market.checkout.domain.product;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ProductCode}
 */
public class ProductCodeTest {

    private static final String CORRECT_CODE = "836430990129";
    private static final String TOO_LONG_CODE = "2136222360410";

    @Test
    public void shouldCreateProductCodeForCorrectValue() {
        ProductCode productCode = ProductCode.of(CORRECT_CODE);

        assertEquals(CORRECT_CODE, productCode.getCode());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullCode() {
        ProductCode.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForEmptyCode() {
        ProductCode.of("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForTooLongCode() {
        ProductCode.of(TOO_LONG_CODE);
    }

}
