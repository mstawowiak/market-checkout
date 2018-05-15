package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.common.Money;
import org.junit.Before;
import org.junit.Test;

import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.BEER;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.MULTI_PROMOTION_B;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link BasketMultiPromotionItem}
 */
public class BasketMultiPromotionItemTest {

    private static final Money SPECIAL_PRICE = Money.of("15");

    private Basket basket;
    private BasketMultiPromotionItem item;

    @Before
    public void before() {
        basket = mock(Basket.class);
        item = BasketMultiPromotionItem.of(basket, BEER, MULTI_PROMOTION_B);
    }

    @Test
    public void shouldCreateBasketMultiPromotionItem() {
        assertEquals(basket.getBasketId(), item.getBasketId());
        assertEquals(BEER, item.getProduct());
        assertEquals(SPECIAL_PRICE, item.getPrice());
        assertEquals(1, item.getQuantity());
        assertEquals(2, item.getUnit());
        assertEquals(SPECIAL_PRICE, item.getTotalPrice());
    }

    @Test
    public void shouldIncrementQuantity() {
        item.incQuantity();
        item.incQuantity();
        item.incQuantity();

        assertEquals(4, item.getQuantity());
    }

    @Test
    public void shouldCorrectCalculateTotalPrice() {
        item.incQuantity();
        item.incQuantity();
        item.incQuantity();

        assertEquals(Money.of("60"), item.getTotalPrice());
    }

}
