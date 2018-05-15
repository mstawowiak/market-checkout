package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import org.junit.Before;
import org.junit.Test;

import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.APPLE;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.BEER;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.COCA_COLA;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.EGGPLANT;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.NO_PROMOTIONS;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.PROMOTIONS;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests for {@link Basket}
 */
public class BasketTest {

    private Basket basket;
    private Basket basketWithoutPromotions;

    @Before
    public void before() {
        basket = new Basket(BasketId.generate(), PROMOTIONS);
        basketWithoutPromotions = new Basket(BasketId.generate(), NO_PROMOTIONS);
    }

    @Test
    public void shouldCreateBasket() {
        assertNotNull(basket.getBasketId());
        assertNotNull(basket.getStartDate());
        assertNotNull(basket.getPromotions());

        assertEquals(BasketStatus.DRAFT, basket.getBasketStatus());
        assertEquals(Money.ZERO, basket.getTotalCost());

        assertNull(basket.getFinishDate());

        assertTrue(basket.getItems().isEmpty());
        assertTrue(basket.getItemsMultiPromotion().isEmpty());
        assertTrue(basket.getAppliedBuyTogetherPromotions().isEmpty());
    }

    @Test
    public void shouldAddOneProduct() {
        int expectedBasketItemsSize = 1;
        Money expectedTotalCost = Money.of("40");

        //when
        basket.addProduct(APPLE);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldAddTwoProducts() {
        int expectedBasketItemsSize = 2;
        Money expectedTotalCost = Money.of("50");

        //when
        basket.addProduct(APPLE);
        basket.addProduct(BEER);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldAddFewProductsToBasketWithoutPromotions() {
        int expectedBasketItemsSize = 4;
        Money expectedTotalCost = Money.of("105");

        //when
        basketWithoutPromotions.addProduct(APPLE);
        basketWithoutPromotions.addProduct(BEER);
        basketWithoutPromotions.addProduct(COCA_COLA);
        basketWithoutPromotions.addProduct(EGGPLANT);

        //then
        assertEquals(expectedBasketItemsSize, basketWithoutPromotions.getItems().size());
        assertEquals(expectedTotalCost, basketWithoutPromotions.getTotalCost());
    }

    @Test
    public void shouldApplyBuyMultiPromotionForOneTypeOfProduct() {
        int expectedBasketItemsSize = 0;
        int expectedBasketItemsMultiPromotionSize = 1;
        Money expectedTotalCost = Money.of("70");

        //when
        basket.addProduct(APPLE);
        basket.addProduct(APPLE);
        basket.addProduct(APPLE);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedBasketItemsMultiPromotionSize, basket.getItemsMultiPromotion().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldApplyBuyMultiPromotionTwiceForOneTypeOfProduct() {
        int expectedBasketItemsSize = 0;
        int expectedBasketItemsMultiPromotionSize = 1;
        Money expectedTotalCost = Money.of("30");

        //when
        basket.addProduct(BEER);
        basket.addProduct(BEER);
        basket.addProduct(BEER);
        basket.addProduct(BEER);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedBasketItemsMultiPromotionSize, basket.getItemsMultiPromotion().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldApplyOneBuyTogetherPromotion() {
        int expectedBasketItemsSize = 2;
        int expectedAppliedBuyTogetherPromotionsSize = 1;
        Money expectedTotalCost = Money.of("35");

        //when
        basket.addProduct(BEER);
        basket.addProduct(COCA_COLA);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedAppliedBuyTogetherPromotionsSize, basket.getAppliedBuyTogetherPromotions().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldApplyTwoBuyTogetherPromotion() {
        int expectedBasketItemsSize = 4;
        int expectedAppliedBuyTogetherPromotionsSize = 2;
        Money expectedTotalCost = Money.of("90");

        //when
        basket.addProduct(APPLE);
        basket.addProduct(BEER);
        basket.addProduct(COCA_COLA);
        basket.addProduct(EGGPLANT);

        //then
        assertEquals(expectedBasketItemsSize, basket.getItems().size());
        assertEquals(expectedAppliedBuyTogetherPromotionsSize, basket.getAppliedBuyTogetherPromotions().size());
        assertEquals(expectedTotalCost, basket.getTotalCost());
    }

    @Test
    public void shouldPayForBasket() {
        basket.pay();

        assertEquals(BasketStatus.PAID, basket.getBasketStatus());
        assertNotNull(basket.getFinishDate());
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenAddProductForPaidBasket() {
        basket.pay();
        basket.addProduct(APPLE);
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenPayForAlreadyPaidBasket() {
        basket.pay();
        basket.pay();
    }

}
