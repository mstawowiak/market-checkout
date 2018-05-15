package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class BuyMultiPromotionJpaRepositoryAdapter implements BuyMultiPromotionRepository {

    private final BuyMultiPromotionJpaRepository dao;

    @Override
    public Iterable<BuyMultiPromotion> findAll() {
        return dao.findAll();
    }
}