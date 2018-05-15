package com.mstawowiak.market.checkout.domain.basket;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotion;
import com.mstawowiak.market.checkout.domain.promotion.Promotions;
import java.util.Collections;
import java.util.Set;

public interface TestDataProvider {

    Product APPLE     = product("123456789012", "Apple", "40");
    Product BEER      = product("836430990129", "Beer", "10");
    Product COCA_COLA = product("213622236041", "Coca-Cola", "30");
    Product EGGPLANT  = product("725272730701", "Eggplant", "25");

    BuyMultiPromotion MULTI_PROMOTION_A = buyMultiPromotion("MULTI-0001", APPLE, "Buy 3 apples for 70", 3, "70");
    BuyMultiPromotion MULTI_PROMOTION_B = buyMultiPromotion("MULTI-0002", BEER, "Buy 2 beers for 15", 2, "15");
    BuyMultiPromotion MULTI_PROMOTION_C = buyMultiPromotion("MULTI-0003", COCA_COLA, "Buy 4 for 60", 4, "60");
    BuyMultiPromotion MULTI_PROMOTION_D = buyMultiPromotion("MULTI-0004", EGGPLANT, "Buy 2 eggplants for 40", 2, "40");

    BuyTogetherPromotion TOGETHER_PROMOTION_A = buyTogetherPromotion("TOG-0001", "Buy Apple with Eggplant",
            ImmutableSet.of(APPLE, EGGPLANT), "10");
    BuyTogetherPromotion TOGETHER_PROMOTION_B = buyTogetherPromotion("TOG-0002", "Buy Beer with Coca-Cola",
            ImmutableSet.of(BEER, COCA_COLA), "5");

    Promotions NO_PROMOTIONS = new Promotions(Collections.emptyList(), Collections.emptyList());
    Promotions PROMOTIONS = new Promotions(
            ImmutableList.of(MULTI_PROMOTION_A, MULTI_PROMOTION_B, MULTI_PROMOTION_C, MULTI_PROMOTION_D),
            ImmutableList.of(TOGETHER_PROMOTION_A, TOGETHER_PROMOTION_B));

    static Product product(String code, String name, String price) {
        return Product.builder()
                .code(ProductCode.of(code))
                .name(name)
                .price(Money.of(price))
                .build();
    }

    static BuyMultiPromotion buyMultiPromotion(String code, Product product, String name,
                                               int requiredQuantity, String specialPrice) {
        return BuyMultiPromotion.builder()
                .code(code)
                .name(name)
                .product(product)
                .requiredQuantity(requiredQuantity)
                .specialPrice(Money.of(specialPrice))
                .build();
    }

    static BuyTogetherPromotion buyTogetherPromotion(String code, String name,
                                                     Set<Product> products, String discount) {
        return BuyTogetherPromotion.builder()
                .code(code)
                .name(name)
                .requiredProducts(products)
                .discount(Money.of(discount))
                .build();
    }
}
