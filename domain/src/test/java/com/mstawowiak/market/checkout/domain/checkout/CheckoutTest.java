package com.mstawowiak.market.checkout.domain.checkout;

import com.mstawowiak.market.checkout.domain.basket.Basket;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import com.mstawowiak.market.checkout.domain.product.Product;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link Checkout}
 */
public class CheckoutTest {

    private static final CheckoutNo CHECKOUT_NO_1 = CheckoutNo.of(1);

    private ReceiptPrinter receiptPrinter;
    private Checkout checkout;
    private Basket basket;

    @Before
    public void before() {
        receiptPrinter = mock(ReceiptPrinter.class);
        CheckoutFactory checkoutFactory = new CheckoutFactory();
        checkout = checkoutFactory.create(CHECKOUT_NO_1, receiptPrinter);
        basket = mock(Basket.class);
    }

    @Test
    public void shouldNoBasketBeAssignedAfterCreateCheckout() {
        assertFalse(checkout.isBasketAssigned());
    }

    @Test
    public void shouldAssignNewBasket() {
        checkout.assignNewBasket(basket);

        assertTrue(checkout.isBasketAssigned());
    }

    @Test
    public void shouldAddNewProduct() {
        Product product = mock(Product.class);

        checkout.assignNewBasket(basket);
        checkout.addProduct(product);

        verify(basket).addProduct(product);
    }

    @Test
    public void shouldGetTotalCost() {
        Money expectedTotalCost = Money.of("24.99");
        when(basket.getTotalCost()).thenReturn(expectedTotalCost);

        checkout.assignNewBasket(basket);
        Money totalCost = checkout.totalCost();

        assertEquals(expectedTotalCost, totalCost);
    }

    @Test
    public void shouldPayAndGetReceipt() {
        String expectedReceipt = "RECEIPT";
        when(receiptPrinter.print(basket)).thenReturn(expectedReceipt);

        checkout.assignNewBasket(basket);
        String receipt = checkout.payAndGetReceipt();

        assertEquals(expectedReceipt, receipt);
    }

    @Test
    public void shouldNoBasketBeAssignedAfterPayAndGetReceipt() {
        checkout.assignNewBasket(basket);
        checkout.payAndGetReceipt();

        assertFalse(checkout.isBasketAssigned());
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenAssignBasketIfAnotherIsAssigned() {
        Basket secondBasket = mock(Basket.class);

        checkout.assignNewBasket(basket);
        checkout.assignNewBasket(secondBasket);
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenAddProductIfBasketIsNotAssigned() {
        Product product = mock(Product.class);

        checkout.addProduct(product);
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenGetTotalCostIfBasketIsNotAssigned() {
        checkout.totalCost();
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenPayAndGetReceiptIfBasketIsNotAssigned() {
        checkout.payAndGetReceipt();
    }
}
