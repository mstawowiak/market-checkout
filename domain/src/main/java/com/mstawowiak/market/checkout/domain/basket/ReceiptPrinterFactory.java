package com.mstawowiak.market.checkout.domain.basket;

import org.springframework.stereotype.Component;

@Component
public class ReceiptPrinterFactory {

    public ReceiptPrinter create() {
        return new DefaultReceiptPrinter();
    }

}
