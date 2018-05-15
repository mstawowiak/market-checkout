package com.mstawowiak.market.checkout.domain.basket;

import com.mstawowiak.market.checkout.domain.common.BaseEntity;
import com.mstawowiak.market.checkout.domain.common.Money;
import com.mstawowiak.market.checkout.domain.common.exception.MarketCheckoutOperationException;
import com.mstawowiak.market.checkout.domain.product.Product;
import com.mstawowiak.market.checkout.domain.promotion.BuyMultiPromotion;
import com.mstawowiak.market.checkout.domain.promotion.BuyTogetherPromotion;
import com.mstawowiak.market.checkout.domain.promotion.Promotions;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static java.util.stream.Stream.concat;

@Entity
@Getter(AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Basket extends BaseEntity {

    @Getter
    @Column(name = "basket_id", nullable = false, unique = true)
    private BasketId basketId;

    @Enumerated
    @Column(name = "basket_status", nullable = false)
    private BasketStatus basketStatus = BasketStatus.DRAFT;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "finish_date")
    private LocalDateTime finishDate;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BasketItem> items = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<BasketMultiPromotionItem> itemsMultiPromotion = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "basket_buy_together_promotion",
            joinColumns = { @JoinColumn(name = "basket_id") },
            inverseJoinColumns = { @JoinColumn(name = "buy_together_promotion_id") }
    )
    private Set<BuyTogetherPromotion> appliedBuyTogetherPromotions = new HashSet<>();

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "total_cost_amount")),
            @AttributeOverride(name = "currencyCode", column = @Column(name = "total_cost_currency_code"))})
    private Money totalCost = Money.ZERO;

    @Transient
    private Promotions promotions;

    public Basket(BasketId basketId, Promotions promotions) {
        this.basketId = basketId;
        this.startDate = LocalDateTime.now();

        this.promotions = promotions;
    }

    public void addProduct(Product product) {
        checkIfDraft();

        addProductToItems(product);

        applyBuyMultiPromotion(product);
        applyBuyTogetherPromotions();

        recalculateTotalCost();
    }

    public void pay() {
        checkIfDraft();

        this.finishDate = LocalDateTime.now();
        this.basketStatus = BasketStatus.PAID;
    }

    private void addProductToItems(Product product) {
        Optional<BasketItem> optionalBasketItem = findBasketItem(product);
        if (optionalBasketItem.isPresent()) {
            optionalBasketItem.get().incQuantity();
        } else {
            BasketItem basketItem = BasketItem.of(this, product);
            items.add(basketItem);
        }
    }

    private Optional<BasketItem> findBasketItem(Product product) {
        return items.stream()
                .filter(item -> item.isForProduct(product))
                .findFirst();
    }

    private void applyBuyMultiPromotion(Product product) {
        BasketItem basketItem = findBasketItem(product).get();
        int quantity = basketItem.getQuantity();

        Optional<BuyMultiPromotion> optionalBuyMultiPromotion = promotions.getMultiPromotionFor(product, quantity);
        if (optionalBuyMultiPromotion.isPresent()) {
            addProductToItemsMultiPromotion(product, optionalBuyMultiPromotion.get());
            items.remove(basketItem);
        }
    }

    private void addProductToItemsMultiPromotion(Product product, BuyMultiPromotion buyMultiPromotion) {
        Optional<BasketMultiPromotionItem> optionalBasketMultiPromotionItem = findBasketMultiPromotionItem(product);
        if (optionalBasketMultiPromotionItem.isPresent()) {
            optionalBasketMultiPromotionItem.get().incQuantity();
        } else {
            BasketMultiPromotionItem item = BasketMultiPromotionItem.of(this, product, buyMultiPromotion);
            itemsMultiPromotion.add(item);
        }
    }

    private Optional<BasketMultiPromotionItem> findBasketMultiPromotionItem(Product product) {
        return itemsMultiPromotion.stream()
                .filter(item -> item.getProduct().equals(product))
                .findFirst();
    }

    private void applyBuyTogetherPromotions() {
        Set<Product> productsInBasket = concat(
                items.stream().map(item -> item.getProduct()),
                itemsMultiPromotion.stream().map(item -> item.getProduct()))
                .collect(Collectors.toSet());

        appliedBuyTogetherPromotions.clear();
        Set<BuyTogetherPromotion> actualPromotions = promotions.getBuyTogetherPromotionsFor(productsInBasket);
        appliedBuyTogetherPromotions.addAll(actualPromotions);
    }

    private void recalculateTotalCost() {
        Money itemsSum = items.stream()
                .map(item -> item.getTotalPrice())
                .reduce(Money::add)
                .orElse(Money.ZERO);
        Money itemsMultiPromotionSum = itemsMultiPromotion.stream()
                .map(item -> item.getTotalPrice())
                .reduce(Money::add)
                .orElse(Money.ZERO);
        Money discount = appliedBuyTogetherPromotions.stream()
                .map(promotion -> promotion.getDiscount())
                .reduce(Money::add)
                .orElse(Money.ZERO);

        totalCost = itemsSum.add(itemsMultiPromotionSum).subtract(discount);
    }

    private void checkIfDraft() {
        if (basketStatus != BasketStatus.DRAFT) {
            throw new MarketCheckoutOperationException("Operation allowed only in %s status", BasketStatus.DRAFT);
        }
    }
}
