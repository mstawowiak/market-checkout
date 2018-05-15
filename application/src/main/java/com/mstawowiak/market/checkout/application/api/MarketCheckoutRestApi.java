package com.mstawowiak.market.checkout.application.api;

import com.mstawowiak.market.checkout.application.service.MarketCheckoutService;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutNo;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1/checkouts/{checkoutNo}")
@RequiredArgsConstructor
class MarketCheckoutRestApi {

    private final MarketCheckoutService marketCheckoutService;

    private static final String CHECKOUT_NO = "checkoutNo";
    private static final String PRODUCT_CODE = "productCode";

    @PostMapping
    public void openCheckout(@PathVariable(CHECKOUT_NO) @Min(1) final Integer checkoutNo) {
        marketCheckoutService.openCheckout(CheckoutNo.of(checkoutNo));
    }

    @DeleteMapping
    public void closeCheckout(@PathVariable(CHECKOUT_NO) @Min(1) final Integer checkoutNo) {
        marketCheckoutService.closeCheckout(CheckoutNo.of(checkoutNo));
    }

    @PostMapping("basket")
    public void createNewBasket(@PathVariable(CHECKOUT_NO) @Min(1) final Integer checkoutNo) {
        marketCheckoutService.createNewBasket(CheckoutNo.of(checkoutNo));
    }

    @PutMapping("basket/scan/{productCode}")
    public String scanProduct(@PathVariable(CHECKOUT_NO) @Min(1) final Integer checkoutNo,
                              @PathVariable(PRODUCT_CODE) @Size(max = 12) final String productCode) {

        return marketCheckoutService.scanProduct(CheckoutNo.of(checkoutNo), ProductCode.of(productCode));
    }

    @PutMapping("basket/pay")
    public String pay(@PathVariable(CHECKOUT_NO) @Min(1) final Integer checkoutNo) {

        return marketCheckoutService.pay(CheckoutNo.of(checkoutNo));
    }
}
