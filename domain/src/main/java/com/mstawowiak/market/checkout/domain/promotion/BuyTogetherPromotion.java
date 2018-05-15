package com.mstawowiak.market.checkout.domain.promotion;

import com.mstawowiak.market.checkout.domain.common.BaseEntity;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BuyTogetherPromotion extends BaseEntity {

    @Getter
    @Column(name = "code", nullable = false)
    private String code;

    @Getter
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "buy_together_promotion_product",
            joinColumns = { @JoinColumn(name = "buy_together_promotion_id") },
            inverseJoinColumns = { @JoinColumn(name = "product_id") }
    )
    private Set<Product> requiredProducts;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "discount_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "discount_currency_code")) })
    private Money discount;

    boolean canBeApplyFor(Set<Product> products) {
        return products.containsAll(requiredProducts);
    }

}
