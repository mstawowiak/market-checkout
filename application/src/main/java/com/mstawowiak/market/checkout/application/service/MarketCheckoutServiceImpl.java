package com.mstawowiak.market.checkout.application.service;

import com.mstawowiak.market.checkout.domain.basket.Basket;
import com.mstawowiak.market.checkout.domain.basket.BasketFactory;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinter;
import com.mstawowiak.market.checkout.domain.basket.ReceiptPrinterFactory;
import com.mstawowiak.market.checkout.domain.checkout.Checkout;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutFactory;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutNo;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.product.ProductRepository;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
class MarketCheckoutServiceImpl implements MarketCheckoutService {

    private final CheckoutFactory checkoutFactory;
    private final ReceiptPrinterFactory receiptPrinterFactory;
    private final BasketFactory basketFactory;
    private final ProductRepository productRepository;

    private final Map<CheckoutNo, Checkout> openCheckouts = new ConcurrentHashMap<>();

    @Override
    public void openCheckout(CheckoutNo checkoutNo) {
        checkIfClosed(checkoutNo);

        ReceiptPrinter receiptPrinter = receiptPrinterFactory.create();
        Checkout checkout = checkoutFactory.create(checkoutNo, receiptPrinter);
        openCheckouts.put(checkoutNo, checkout);
        log.info("Checkout {} - open", checkoutNo);
    }

    @Override
    public void closeCheckout(CheckoutNo checkoutNo) {
        checkIfOpen(checkoutNo);

        openCheckouts.remove(checkoutNo);
        log.info("Checkout {} - close", checkoutNo);
    }

    @Override
    public void createNewBasket(CheckoutNo checkoutNo) {
        Basket basket = basketFactory.create();
        getCheckout(checkoutNo).assignNewBasket(basket);
        log.info("Checkout {} - creating new basket [{}]", checkoutNo, basket.getBasketId());
    }

    @Override
    public String scanProduct(CheckoutNo checkoutNo, ProductCode productCode) {
        Optional<Product> product = productRepository.findByCode(productCode);
        if (!product.isPresent()) {
            throw new MarketCheckoutOperationException("Product %s does not exists", productCode.getCode());
        }

        Checkout checkout = getCheckout(checkoutNo);

        checkout.addProduct(product.get());

        String currentTotalCost = checkout.totalCost().toString();

        log.info("Checkout {} - product '{}' added to basket [currentTotalCost={}]",
                checkoutNo, productCode.getCode(), currentTotalCost);

        return currentTotalCost;
    }

    @Override
    public String pay(CheckoutNo checkoutNo) {
        Checkout checkout = getCheckout(checkoutNo);

        String receipt = checkout.payAndGetReceipt();

        log.info("Checkout {} - pay for basket", checkoutNo);
        log.info(receipt);

        return receipt;
    }

    private Checkout getCheckout(CheckoutNo checkoutNo) {
        Checkout checkout = openCheckouts.get(checkoutNo);

        if (checkout == null) {
            throw new MarketCheckoutOperationException("Checkout %s is closed", checkoutNo);
        }
        return checkout;
    }

    private boolean isOpen(CheckoutNo checkoutNo) {
        return openCheckouts.containsKey(checkoutNo);
    }

    private void checkIfOpen(CheckoutNo checkoutNo) {
        if (!isOpen(checkoutNo)) {
            throw new MarketCheckoutOperationException("Checkout %s is already closed", checkoutNo);
        }
    }

    private void checkIfClosed(CheckoutNo checkoutNo) {
        if (isOpen(checkoutNo)) {
            throw new MarketCheckoutOperationException("Checkout %s is already opened", checkoutNo);
        }
    }
}
