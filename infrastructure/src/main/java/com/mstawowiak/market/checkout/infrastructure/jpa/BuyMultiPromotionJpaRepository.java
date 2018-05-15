package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuyMultiPromotionJpaRepository extends CrudRepository<BuyMultiPromotion, Long> {
}
