package com.mstawowiak.market.checkout.domain.promotion;

import com.mstawowiak.market.checkout.domain.product.Product;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Promotions {

    private final List<BuyMultiPromotion> buyMultiPromotions;
    private final List<BuyTogetherPromotion> buyTogetherPromotions;

    public Optional<BuyMultiPromotion> getMultiPromotionFor(Product product, int quantity) {
        return buyMultiPromotions.stream()
                .filter(promotion -> promotion.isFor(product, quantity))
                .findFirst();
    }

    public Set<BuyTogetherPromotion> getBuyTogetherPromotionsFor(Set<Product> products) {
        return buyTogetherPromotions.stream()
                .filter(promotion -> promotion.canBeApplyFor(products))
                .collect(Collectors.toSet());
    }
}
