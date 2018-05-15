package com.mstawowiak.market.checkout.domain.common.exception;

public class MarketCheckoutOperationException extends RuntimeException {

    public MarketCheckoutOperationException(String message) {
        super(message);
    }

    public MarketCheckoutOperationException(String message, Object... params) {
        super(String.format(message, params));
    }
}
