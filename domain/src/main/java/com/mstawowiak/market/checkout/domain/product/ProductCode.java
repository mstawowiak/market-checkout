package com.mstawowiak.market.checkout.domain.product;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static lombok.AccessLevel.PRIVATE;

@Value
@RequiredArgsConstructor(access = PRIVATE)
public class ProductCode {

    static final Integer CODE_MAX_LENGTH = 12;

    private final String code;

    public static ProductCode of(String code) {
        checkArgument(!isNullOrEmpty(code), "productCode cannot be null or empty");
        checkArgument(code.length() <= CODE_MAX_LENGTH,
                String.format("productCode must be <= %d but is %s", CODE_MAX_LENGTH, code));

        return new ProductCode(code);
    }
}
