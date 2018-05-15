package com.mstawowiak.market.checkout.domain.product;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ProductCodeJpaConverter implements AttributeConverter<ProductCode, String> {

    @Override
    public String convertToDatabaseColumn(ProductCode productCode) {
        return productCode.getCode();
    }

    @Override
    public ProductCode convertToEntityAttribute(String code) {
        return ProductCode.of(code);
    }
}
