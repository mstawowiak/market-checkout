package com.mstawowiak.market.checkout.domain.checkout;

import com.mstawowiak.market.checkout.domain.basket.Basket;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import com.mstawowiak.market.checkout.domain.product.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Checkout {

    @Getter
    private final CheckoutNo checkoutNo;

    private final ReceiptPrinter receiptPrinter;

    private Basket currentBasket;

    public void assignNewBasket(Basket basket) {
        if (isBasketAssigned()) {
            throw new MarketCheckoutOperationException("Basket is already assigned to this checkout");
        }
        currentBasket = basket;
    }

    public void addProduct(Product product) {
        checkIfBasketIsAssigned();

        currentBasket.addProduct(product);
    }

    public Money totalCost() {
        checkIfBasketIsAssigned();

        return currentBasket.getTotalCost();
    }

    public String payAndGetReceipt() {
        checkIfBasketIsAssigned();

        currentBasket.pay();

        String receipt = receiptPrinter.print(currentBasket);
        currentBasket = null;

        return receipt;
    }

    private void checkIfBasketIsAssigned() {
        if (!isBasketAssigned()) {
            throw new MarketCheckoutOperationException("No basket is assigned to this checkout");
        }
    }

    boolean isBasketAssigned() {
        return currentBasket != null;
    }

}
