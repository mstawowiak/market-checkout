package com.mstawowiak.market.checkout.application.api;

import com.mstawowiak.market.checkout.application.service.MarketCheckoutService;
import com.mstawowiak.market.checkout.domain.checkout.CheckoutNo;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(MarketCheckoutRestApi.class)
public class MarketCheckoutRestApiIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MarketCheckoutService marketCheckoutService;

    private static final CheckoutNo CHECKOUT_NO_1 = CheckoutNo.of(1);
    private static final String PRODUCT_CODE_VALUE = "836430990129";
    private static final ProductCode PRODUCT_CODE = ProductCode.of(PRODUCT_CODE_VALUE);

    @Test
    public void shouldOpenCheckout() throws Exception {
        doNothing().when(marketCheckoutService).openCheckout(CHECKOUT_NO_1);

        mockMvc.perform(post("/api/v1/checkouts/1"))
                .andExpect(status().isOk());

        verify(marketCheckoutService).openCheckout(CHECKOUT_NO_1);
    }

    @Test
    public void shouldCloseCheckout() throws Exception {
        doNothing().when(marketCheckoutService).closeCheckout(CHECKOUT_NO_1);

        mockMvc.perform(delete("/api/v1/checkouts/1"))
                .andExpect(status().isOk());

        verify(marketCheckoutService).closeCheckout(CHECKOUT_NO_1);
    }

    @Test
    public void shouldCreateNewBasket() throws Exception {
        doNothing().when(marketCheckoutService).createNewBasket(CHECKOUT_NO_1);

        mockMvc.perform(post("/api/v1/checkouts/1/basket"))
                .andExpect(status().isOk());

        verify(marketCheckoutService).createNewBasket(CHECKOUT_NO_1);
    }

    @Test
    public void shouldScanProductAndReturnActualTotalCost() throws Exception {
        String expectedTotalCost = "100.00 EUR";
        when(marketCheckoutService.scanProduct(CHECKOUT_NO_1, PRODUCT_CODE)).thenReturn(expectedTotalCost);

        mockMvc.perform(put("/api/v1/checkouts/1/basket/scan/" + PRODUCT_CODE_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(containsString(expectedTotalCost)));

        verify(marketCheckoutService).scanProduct(CHECKOUT_NO_1, PRODUCT_CODE);
    }

    @Test
    public void shouldReturnReceiptAfterPayment() throws Exception {
        String expectedReceipt = "RECEIPT";
        when(marketCheckoutService.pay(CHECKOUT_NO_1)).thenReturn(expectedReceipt);

        mockMvc.perform(put("/api/v1/checkouts/1/basket/pay"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(content().string(containsString(expectedReceipt)));

        verify(marketCheckoutService).pay(CHECKOUT_NO_1);
    }

}
