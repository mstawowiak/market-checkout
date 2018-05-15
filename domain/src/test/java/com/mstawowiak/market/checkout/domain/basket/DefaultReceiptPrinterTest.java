package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.promotion.PromotionsFactory;
import org.junit.Before;
import org.junit.Test;

import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.APPLE;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.BEER;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.COCA_COLA;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.EGGPLANT;
import static com.mstawowiak.market.checkout.domain.basket.TestDataProvider.PROMOTIONS;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link DefaultReceiptPrinter}
 */
public class DefaultReceiptPrinterTest {

    private ReceiptPrinter receiptPrinter;
    private Basket basket;

    @Before
    public void before() {
        receiptPrinter = new DefaultReceiptPrinter();

        PromotionsFactory promotionsFactory = mock(PromotionsFactory.class);
        when(promotionsFactory.create()).thenReturn(PROMOTIONS);
        BasketFactory basketFactory = new BasketFactory(promotionsFactory);

        basket = basketFactory.create();
    }

    @Test
    public void shouldPrintSimpleReceipt() {
        String expectedAppleLine = "Apple  1  40,00 EUR       40,00 EUR";
        String expectedBeerLine  = "Beer   1  10,00 EUR       10,00 EUR";

        basket.addProduct(APPLE);
        basket.addProduct(BEER);

        basket.pay();

        String receipt = receiptPrinter.print(basket);

        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedAppleLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedBeerLine));
    }

    @Test
    public void shouldPrintReceiptWithPromotions() {
        String expectedBeerLine      = "Beer                               1       10,00 EUR       10,00 EUR";
        String expectedAppleLine     = "Apple                              1       40,00 EUR       40,00 EUR";
        String expectedCocaColaLine  = "Coca-Cola                          2       30,00 EUR       60,00 EUR";
        String expectedEggplantLine  = "Eggplant                           1       25,00 EUR       25,00 EUR";
        String expectedBeerMultiLine = "Beer (Buy 2 beers for 15)          2       15,00 EUR       30,00 EUR";

        String expectedDiscountBeerCocaColaLine = "Buy Beer with Coca-Cola                         -5,00 EUR";
        String expectedDiscountAppleEggplantLine = "Buy Apple with Eggplant                       -10,00 EUR";

        basket.addProduct(APPLE);

        basket.addProduct(BEER);
        basket.addProduct(BEER);
        basket.addProduct(BEER);
        basket.addProduct(BEER);
        basket.addProduct(BEER);

        basket.addProduct(COCA_COLA);
        basket.addProduct(COCA_COLA);

        basket.addProduct(EGGPLANT);

        basket.pay();

        String receipt = receiptPrinter.print(basket);

        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedAppleLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedBeerLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedCocaColaLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedEggplantLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedBeerMultiLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedDiscountBeerCocaColaLine));
        assertTrue(containsIgnoringWhiteSpaces(receipt, expectedDiscountAppleEggplantLine));
    }

    private static boolean containsIgnoringWhiteSpaces(String base, String toFind) {
        return base.replaceAll("\\s+","").contains(toFind.replaceAll("\\s+",""));
    }
}
