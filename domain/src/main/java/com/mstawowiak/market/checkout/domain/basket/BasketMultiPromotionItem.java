package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.common.BaseEntity;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
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

@Entity
class BasketMultiPromotionItem extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @Getter(AccessLevel.PACKAGE)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "buy_multi_promotion_id", nullable = false)
    private BuyMultiPromotion buyMultiPromotion;

    @Getter(AccessLevel.PACKAGE)
    @Column(name = "quantity", nullable = false)
    private int quantity = 1;

    @Getter(AccessLevel.PACKAGE)
    @Column(name = "unit", nullable = false)
    private int unit;

    @Getter(AccessLevel.PACKAGE)
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "price_currency_code"))})
    private Money price;

    static BasketMultiPromotionItem of(Basket basket, Product product, BuyMultiPromotion buyMultiPromotion) {
        BasketMultiPromotionItem item = new BasketMultiPromotionItem();

        item.basket = basket;
        item.product = product;
        item.price = buyMultiPromotion.getSpecialPrice();
        item.unit = buyMultiPromotion.getRequiredQuantity();
        item.buyMultiPromotion = buyMultiPromotion;

        return item;
    }

    BasketId getBasketId() {
        return basket.getBasketId();
    }

    String getProductName() {
        return product.getName();
    }

    String getPromotionName() {
        return buyMultiPromotion.getName();
    }

    Money getTotalPrice() {
        return price.multiplyBy(quantity);
    }

    void incQuantity() {
        quantity++;
    }
}
