package com.mstawowiak.market.checkout.application.service;

import com.mstawowiak.market.checkout.domain.basket.Basket;
import com.mstawowiak.market.checkout.domain.basket.BasketFactory;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinterFactory;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutFactory;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutNo;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.product.ProductRepository;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Tests for {@link MarketCheckoutServiceImpl}
 */
public class MarketCheckoutServiceImplTest {

    private MarketCheckoutServiceImpl marketCheckoutService;

    private CheckoutFactory checkoutFactory;
    private ReceiptPrinterFactory receiptPrinterFactory;
    private ReceiptPrinter receiptPrinter;
    private BasketFactory basketFactory;
    private ProductRepository productRepository;
    private Basket basket;

    private static final CheckoutNo CHECKOUT_NO_1 = CheckoutNo.of(1);

    @Before
    public void before() {
        checkoutFactory = new CheckoutFactory();
        receiptPrinterFactory = mock(ReceiptPrinterFactory.class);
        receiptPrinter = mock(ReceiptPrinter.class);
        when(receiptPrinterFactory.create()).thenReturn(receiptPrinter);

        basketFactory = mock(BasketFactory.class);
        productRepository = mock(ProductRepository.class);

        marketCheckoutService = new MarketCheckoutServiceImpl(checkoutFactory,
                receiptPrinterFactory, basketFactory, productRepository);

        basket = mock(Basket.class);
        when(basketFactory.create()).thenReturn(basket);
    }

    @Test
    public void shouldOpenCheckout() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
    }

    @Test
    public void shouldCloseCheckout() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.closeCheckout(CHECKOUT_NO_1);
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenOpenAlreadyOpenedCheckout() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenCloseAlreadyClosedCheckout() {
        marketCheckoutService.closeCheckout(CHECKOUT_NO_1);
    }

    @Test
    public void shouldCreateNewBasket() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);

        verify(basketFactory).create();
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenCreateTwoBasketsForCheckout() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);
    }

    @Test
    public void shouldScanProductAndReturnTotalCost() {
        Product product = mock(Product.class);

        when(productRepository.findByCode(any(ProductCode.class))).thenReturn(Optional.of(product));
        when(basket.getTotalCost()).thenReturn(Money.of("50"));

        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);

        String totalCost = marketCheckoutService.scanProduct(CHECKOUT_NO_1, ProductCode.of("836430990129"));

        assertEquals("50,00 EUR", totalCost);
        verify(productRepository).findByCode(any(ProductCode.class));
    }

    @Test(expected = MarketCheckoutOperationException.class)
    public void shouldThrowExceptionWhenScanProductWhichNotExists() {
        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);
        marketCheckoutService.scanProduct(CHECKOUT_NO_1, ProductCode.of("836430990129"));
    }

    @Test
    public void shouldReturnReceiptWhenPay() {
        when(receiptPrinter.print(any())).thenReturn("RECEIPT");

        marketCheckoutService.openCheckout(CHECKOUT_NO_1);
        marketCheckoutService.createNewBasket(CHECKOUT_NO_1);
        String receipt = marketCheckoutService.pay(CHECKOUT_NO_1);

        assertEquals("RECEIPT", receipt);
    }
}
