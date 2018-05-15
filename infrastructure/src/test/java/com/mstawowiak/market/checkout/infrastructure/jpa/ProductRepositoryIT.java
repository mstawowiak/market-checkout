package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.product.ProductRepository;
import java.util.Optional;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import({ProductJpaRepositoryAdapter.class})
@EntityScan(value = "com.mstawowiak.market.checkout.domain")
public class ProductRepositoryIT {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository repository;

    private static final ProductCode APPLE_PRODUCT_CODE = ProductCode.of("123456789012");
    private static final ProductCode WRONG_PRODUCT_CODE = ProductCode.of("836430990129");

    private Product apple;

    @Before
    public void before() {
        apple = Product.builder()
                .code(APPLE_PRODUCT_CODE)
                .name("Apple")
                .price(Money.of("40"))
                .build();
    }

    @Test
    public void shouldFindNoProductForEmptyRepository() {
        //when
        Optional<Product> productOptional = repository.findByCode(APPLE_PRODUCT_CODE);

        //then
        assertFalse(productOptional.isPresent());
    }

    @Test
    public void shouldFindNoProductForWrongKey() {
        //given
        entityManager.persistAndFlush(apple);

        //when
        Optional<Product> productOptional = repository.findByCode(WRONG_PRODUCT_CODE);

        //then
        assertFalse(productOptional.isPresent());
    }

    @Test
    public void shouldFindProduct() {
        //given
        entityManager.persistAndFlush(apple);

        //when
        Optional<Product> productOptional = repository.findByCode(APPLE_PRODUCT_CODE);

        //then
        assertTrue(productOptional.isPresent());
        assertEquals(apple, productOptional.get());
    }

}
