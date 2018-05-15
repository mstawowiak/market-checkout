package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.promotion.PromotionsFactory;
import org.junit.Before;
import org.junit.Test;

import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.NO_PROMOTIONS;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link BasketFactory}
 */
public class BasketFactoryTest {

    private PromotionsFactory promotionsFactory;

    @Before
    public void before() {
        promotionsFactory = mock(PromotionsFactory.class);
    }

    @Test
    public void shouldCreateBasket() {
        when(promotionsFactory.create()).thenReturn(NO_PROMOTIONS);

        BasketFactory basketFactory = new BasketFactory(promotionsFactory);

        Basket basket = basketFactory.create();

        assertNotNull(basket.getBasketId());
        assertNotNull(basket.getStartDate());
        assertNotNull(basket.getPromotions());

        verify(promotionsFactory).create();
    }
}
