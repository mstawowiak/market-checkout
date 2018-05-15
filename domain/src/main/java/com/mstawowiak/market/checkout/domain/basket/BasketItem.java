package com.mstawowiak.market.checkout.domain.basket;

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
import javax.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
class BasketItem extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Getter(AccessLevel.PACKAGE)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Getter(AccessLevel.PACKAGE)
    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    @Getter(AccessLevel.PACKAGE)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "price_currency_code"))})
    private Money price;

    static BasketItem of(Basket basket, Product product) {
        BasketItem item = new BasketItem();

        item.basket = basket;
        item.product = product;
        item.price = product.getPrice();

        return item;
    }

    BasketId getBasketId() {
        return basket.getBasketId();
    }

    String getProductName() {
        return product.getName();
    }

    Money getTotalPrice() {
        return price.multiplyBy(quantity);
    }

    void incQuantity() {
        quantity++;
    }

    boolean isForProduct(Product product) {
        return this.product.equals(product);
    }

}
