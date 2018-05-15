package com.mstawowiak.market.checkout.domain.promotion;

import com.mstawowiak.market.checkout.domain.common.BaseEntity;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuyMultiPromotion extends BaseEntity {

    @Getter
    @Column(name = "code", nullable = false)
    private String code;

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Getter
    @Column(name = "required_quantity", nullable = false)
    private int requiredQuantity;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "special_price_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "special_price_currency_code")) })
    private Money specialPrice;

    boolean isFor(Product product, int quantity) {
        return this.product.equals(product) && quantity == requiredQuantity;
    }

}
