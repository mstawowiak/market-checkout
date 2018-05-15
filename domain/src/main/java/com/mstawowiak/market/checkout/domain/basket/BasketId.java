package com.mstawowiak.market.checkout.domain.basket;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static lombok.AccessLevel.PRIVATE;

@Value
@RequiredArgsConstructor(access = PRIVATE)
class BasketId {

    private final String basketId;

    public static BasketId generate() {
        return new BasketId(UUID.randomUUID().toString());
    }

    public static BasketId of(String basketId) {
        checkArgument(!isNullOrEmpty(basketId), "basketId cannot be null or empty");

        return new BasketId(basketId);
    }

    public String asString() {
        return basketId;
    }
}