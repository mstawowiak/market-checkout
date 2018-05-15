package com.mstawowiak.market.checkout.domain.basket;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BasketIdTest {

    private static final String CORRECT_VALUE = "925cbd1c-9f03-4e75-b141-1697270b44d6";

    @Test
    public void shouldCreateBasketIdForCorrectValue() {
        BasketId basketId = BasketId.of(CORRECT_VALUE);

        assertEquals(CORRECT_VALUE, basketId.getBasketId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForNullValue() {
        BasketId.of(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionForEmptyValue() {
        BasketId.of("");
    }

}
