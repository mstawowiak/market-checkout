package com.mstawowiak.market.checkout.domain.promotion;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionsFactory {

    private final BuyMultiPromotionRepository buyMultiPromotionRepository;
    private final BuyTogetherPromotionRepository buyTogetherPromotionRepository;

    public Promotions create() {
        Iterable<BuyMultiPromotion> buyMultiPromotions = buyMultiPromotionRepository.findAll();
        Iterable<BuyTogetherPromotion> buyTogetherPromotions = buyTogetherPromotionRepository.findAll();

        return new Promotions(Lists.newArrayList(buyMultiPromotions), Lists.newArrayList(buyTogetherPromotions));
    }

}
