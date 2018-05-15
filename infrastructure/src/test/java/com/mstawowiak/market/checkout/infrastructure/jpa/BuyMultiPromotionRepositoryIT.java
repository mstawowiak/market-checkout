package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.google.common.collect.Lists;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotionRepository;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BuyMultiPromotionJpaRepositoryAdapter.class)
@EntityScan(value = "com.mstawowiak.market.checkout.domain")
public class BuyMultiPromotionRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BuyMultiPromotionRepository repository;

    private BuyMultiPromotion promotion;
    private Product apple;

    @Before
    public void before() {
        apple = Product.builder()
                .code(ProductCode.of("123456789012"))
                .name("Apple")
                .price(Money.of("40"))
                .build();

        promotion = BuyMultiPromotion.builder()
                .code("MULTI-0001")
                .name("Buy 3 apples for 70")
                .product(apple)
                .requiredQuantity(3)
                .specialPrice(Money.of("70"))
                .build();
    }

    @Test
    public void shouldFindNoPromotionsForEmptyRepository() {
        //when
        List<BuyMultiPromotion> promotions = Lists.newArrayList(repository.findAll());

        //then
        assertTrue(promotions.isEmpty());
    }

    @Test
    public void shouldFindAllPromotions() {
        //given
        entityManager.persistAndFlush(apple);
        entityManager.persistAndFlush(promotion);

        //when
        List<BuyMultiPromotion> promotions = Lists.newArrayList(repository.findAll());

        //then
        assertEquals(1, promotions.size());
        BuyMultiPromotion promotionFromDb = promotions.get(0);
        assertEquals(promotion, promotionFromDb);
        assertEquals(promotion.getCode(), promotionFromDb.getCode());
    }
}
