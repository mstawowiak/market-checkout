package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import com.mstawowiak.market.checkout.domain.product.ProductRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ProductJpaRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository dao;

    @Override
    public Optional<Product> findByCode(ProductCode code) {
        return dao.findByCode(code);
    }

}
