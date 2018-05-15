package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotionRepository;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(BuyTogetherPromotionJpaRepositoryAdapter.class)
@EntityScan(value = "com.mstawowiak.market.checkout.domain")
public class BuyTogetherPromotionRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BuyTogetherPromotionRepository repository;

    private BuyTogetherPromotion promotion;
    private Product apple;
    private Product eggplant;

    @Before
    public void before() {
        apple = Product.builder()
                .code(ProductCode.of("123456789012"))
                .name("Apple")
                .price(Money.of("40"))
                .build();

        eggplant = Product.builder()
                .code(ProductCode.of("725272730701"))
                .name("Eggplant")
                .price(Money.of("25"))
                .build();

        promotion = BuyTogetherPromotion.builder()
                .code("TOG-0001")
                .name("Buy Apple with Eggplant")
                .requiredProducts(ImmutableSet.of(apple, eggplant))
                .discount(Money.of("10"))
                .build();
    }

    @Test
    public void shouldFindNoPromotionsForEmptyRepository() {
        //when
        Iterable<BuyTogetherPromotion> promotions = repository.findAll();

        //then
        assertThat(promotions).isEmpty();
    }

    @Test
    public void shouldFindAllPromotions() {
        //given
        entityManager.persistAndFlush(apple);
        entityManager.persistAndFlush(eggplant);
        entityManager.persistAndFlush(promotion);

        //when
        List<BuyTogetherPromotion> promotions = Lists.newArrayList(repository.findAll());

        //then
        assertEquals(1, promotions.size());

        BuyTogetherPromotion promotionFromDb = promotions.get(0);
        assertEquals(promotion, promotionFromDb);
        assertEquals(promotion.getCode(), promotionFromDb.getCode());
    }
}
