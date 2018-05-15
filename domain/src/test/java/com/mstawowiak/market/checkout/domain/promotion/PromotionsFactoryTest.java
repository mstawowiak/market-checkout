package com.mstawowiak.market.checkout.domain.promotion;

import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link PromotionsFactory}
 */
public class PromotionsFactoryTest {

    private BuyMultiPromotionRepository buyMultiPromotionRepository;
    private BuyTogetherPromotionRepository buyTogetherPromotionRepository;

    @Before
    public void before() {
        buyMultiPromotionRepository = mock(BuyMultiPromotionRepository.class);
        buyTogetherPromotionRepository = mock(BuyTogetherPromotionRepository.class);
    }

    @Test
    public void shouldCreatePromotions() {
        when(buyMultiPromotionRepository.findAll()).thenReturn(Collections.emptyList());
        when(buyTogetherPromotionRepository.findAll()).thenReturn(Collections.emptyList());

        PromotionsFactory promotionsFactory
                = new PromotionsFactory(buyMultiPromotionRepository, buyTogetherPromotionRepository);

        Promotions promotions = promotionsFactory.create();

        assertNotNull(promotions);

        verify(buyMultiPromotionRepository).findAll();
        verify(buyTogetherPromotionRepository).findAll();
    }
}
