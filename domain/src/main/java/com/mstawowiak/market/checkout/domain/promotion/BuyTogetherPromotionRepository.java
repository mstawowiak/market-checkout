package com.mstawowiak.market.checkout.domain.promotion;

public interface BuyTogetherPromotionRepository {

    Iterable<BuyTogetherPromotion> findAll();

}
