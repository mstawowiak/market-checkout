package com.mstawowiak.market.checkout.application;

import com.mstawowiak.market.checkout.application.service.MarketCheckoutService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationIT {

    @Autowired
    private MarketCheckoutService marketCheckoutService;

    @Test
    public void contextLoads() throws Exception {
        assertThat(marketCheckoutService).isNotNull();
    }
}
