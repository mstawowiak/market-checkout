package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.promotion.Promotions;
import com.mstawowiak.market.checkout.domain.promotion.PromotionsFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BasketFactory {

    private final PromotionsFactory promotionsFactory;

    public Basket create() {
        BasketId basketId = BasketId.generate();

        Promotions promotions = promotionsFactory.create();

        return new Basket(basketId, promotions);
    }

}
