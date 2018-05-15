package com.mstawowiak.market.checkout.application.service;

import com.mstawowiak.market.checkout.domain.checkout.CheckoutNo;
import com.mstawowiak.market.checkout.domain.product.ProductCode;

public interface MarketCheckoutService {

    void openCheckout(CheckoutNo checkoutNo);

    void closeCheckout(CheckoutNo checkoutNo);

    void createNewBasket(CheckoutNo checkoutNo);

    String scanProduct(CheckoutNo checkoutNo, ProductCode productCode);

    String pay(CheckoutNo checkoutNo);

}
