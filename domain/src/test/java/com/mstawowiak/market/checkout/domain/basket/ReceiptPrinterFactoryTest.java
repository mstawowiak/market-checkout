package com.mstawowiak.market.checkout.domain.basket;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Tests for {@link ReceiptPrinterFactory}
 */
public class ReceiptPrinterFactoryTest {

    private ReceiptPrinterFactory receiptPrinterFactory;

    @Before
    public void before() {
        receiptPrinterFactory = new ReceiptPrinterFactory();
    }

    @Test
    public void shouldCorrectCreateReceiptPrinter() {
        ReceiptPrinter receiptPrinter = receiptPrinterFactory.create();
        assertNotNull(receiptPrinter);
    }
}
