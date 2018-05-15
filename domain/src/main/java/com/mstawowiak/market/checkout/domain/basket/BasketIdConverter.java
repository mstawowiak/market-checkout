package com.mstawowiak.market.checkout.domain.basket;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BasketIdConverter implements AttributeConverter<BasketId, String> {

    @Override
    public String convertToDatabaseColumn(BasketId basketId) {
        return basketId.asString();
    }

    @Override
    public BasketId convertToEntityAttribute(String basketId) {
        return BasketId.of(basketId);
    }
}
