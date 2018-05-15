package com.mstawowiak.market.checkout.infrastructure.jpa;

import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.product.ProductCode;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
interface ProductJpaRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p where p.code = :code")
    Optional<Product> findByCode(@Param("code") ProductCode code);

}