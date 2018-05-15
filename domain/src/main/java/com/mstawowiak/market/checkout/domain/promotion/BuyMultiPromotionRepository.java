package com.mstawowiak.market.checkout.domain.promotion;

public interface BuyMultiPromotionRepository {

    Iterable<BuyMultiPromotion> findAll();

}
