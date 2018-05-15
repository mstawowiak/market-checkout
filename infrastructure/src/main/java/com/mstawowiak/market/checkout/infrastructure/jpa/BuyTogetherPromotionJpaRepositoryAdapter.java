package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class BuyTogetherPromotionJpaRepositoryAdapter implements BuyTogetherPromotionRepository {

    private final BuyTogetherPromotionJpaRepository dao;

    @Override
    public Iterable<BuyTogetherPromotion> findAll() {
        return dao.findAll();
    }
}
