package com.mstawowiak.market.checkout.domain.basket;

import com.google.common.collect.ImmutableList;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;

public class DefaultReceiptPrinter implements ReceiptPrinter {

    private static final String MARKET_NAME = "PRAGMATIC MARKET";
    private static final String HLINE = "------------------------------------------------------------------------------";

    private static final String MARKET_FORMAT = "%50s";
    private static final String DATE_ID_FORMAT = "%-35s %42s";
    private static final String PRODUCTS_FORMAT = "%-35s %10s %15s %15s";
    private static final String DISCOUNTS_FORMAT = "%-35s %42s";
    private static final String TOTAL_FORMAT = "TOTAL: %71s";

    private static final List<String> PRODUCTS_HEADERS = ImmutableList.of("Product", "Quantity", "Netto", "Total");
    private static final String DISCOUNTS = "Discounts:";

    @Override
    public String print(Basket basket) {
        return new Printer(basket).print();
    }

    @RequiredArgsConstructor
    private static class Printer {

        private final Basket basket;

        public String print() {
            ReceiptBuilder builder = new ReceiptBuilder();

            printHeader(builder);

            builder.line(HLINE);
            printItems(builder);
            printItemsMultiPromotion(builder);
            printBuyTogetherPromotions(builder);
            builder.line(HLINE);
            printTotalCost(builder);

            return builder.build();
        }

        private void printHeader(ReceiptBuilder builder) {
            builder.line(String.format(MARKET_FORMAT, MARKET_NAME));
            builder.line(String.format(DATE_ID_FORMAT,
                    basket.getFinishDate().format(DateTimeFormatter.ISO_DATE),
                    basket.getBasketId().asString()));
            builder.emptyLine();
            builder.line(String.format(PRODUCTS_FORMAT, PRODUCTS_HEADERS.toArray()));
        }

        private void printItems(ReceiptBuilder builder) {
            basket.getItems().forEach(item ->
                    builder.line(String.format(PRODUCTS_FORMAT,
                            item.getProductName(),
                            item.getQuantity(),
                            item.getPrice(),
                            item.getTotalPrice()))
            );
        }

        private void printItemsMultiPromotion(ReceiptBuilder builder) {
            basket.getItemsMultiPromotion().forEach(item ->
                    builder.line(String.format(PRODUCTS_FORMAT,
                            item.getProductName() + " (" + item.getPromotionName() + ")",
                            item.getQuantity(),
                            item.getPrice(),
                            item.getTotalPrice()))
            );
        }

        private void printBuyTogetherPromotions(ReceiptBuilder builder) {
            if (!basket.getAppliedBuyTogetherPromotions().isEmpty()) {
                builder.emptyLine();
                builder.line(DISCOUNTS);
                basket.getAppliedBuyTogetherPromotions().forEach(promotion ->
                        builder.line(String.format(DISCOUNTS_FORMAT,
                                promotion.getName(),
                                "-" + promotion.getDiscount())));
            }
        }

        private void printTotalCost(ReceiptBuilder builder) {
            builder.line(String.format(TOTAL_FORMAT, basket.getTotalCost()));
        }

        private class ReceiptBuilder {

            private final StringBuilder text;

            public ReceiptBuilder() {
                text = new StringBuilder();
            }

            public ReceiptBuilder line(String line) {
                text.append(line).append(System.lineSeparator());
                return this;
            }

            public ReceiptBuilder emptyLine() {
                text.append(System.lineSeparator());
                return this;
            }

            public String build() {
                return text.toString();
            }
        }
    }
}
