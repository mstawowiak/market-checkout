package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BuyTogetherPromotionJpaRepository extends CrudRepository<BuyTogetherPromotion, Long> {
}
