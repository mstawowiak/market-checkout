package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.common.Money;
import org.junit.Before;
import org.junit.Test;

import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.BEER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BasketItem}
 */
public class BasketItemTest {

    private Basket basket;
    private BasketItem basketItem;

    @Before
    public void before() {
        basket = mock(Basket.class);
        basketItem = BasketItem.of(basket, BEER);
    }

    @Test
    public void shouldCreateBasketItem() {
        assertEquals(basket.getBasketId(), basketItem.getBasketId());
        assertEquals(BEER, basketItem.getProduct());
        assertEquals(BEER.getPrice(), basketItem.getPrice());
        assertEquals(1, basketItem.getQuantity());
        assertEquals(BEER.getPrice(), basketItem.getTotalPrice());
    }

    @Test
    public void shouldIncrementQuantity() {
        basketItem.incQuantity();
        basketItem.incQuantity();

        assertEquals(3, basketItem.getQuantity());
    }

    @Test
    public void shouldCorrectCalculateTotalPrice() {
        basketItem.incQuantity();
        basketItem.incQuantity();

        assertEquals(Money.of("30"), basketItem.getTotalPrice());
    }

}
