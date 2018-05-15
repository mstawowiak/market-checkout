package com.mstawowiak.market.checkout.domain.product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findByCode(ProductCode code);

}
