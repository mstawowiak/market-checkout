INSERT INTO PRODUCT (id, code, name, price_amount, price_currency_code) VALUES (1, '123456789012', 'Apple', 40, 'EUR');
INSERT INTO PRODUCT (id, code, name, price_amount, price_currency_code) VALUES (2, '836430990129', 'Beer', 10, 'EUR');
INSERT INTO PRODUCT (id, code, name, price_amount, price_currency_code) VALUES (3, '213622236041', 'Coca-Cola', 30, 'EUR');
INSERT INTO PRODUCT (id, code, name, price_amount, price_currency_code) VALUES (4, '725272730701', 'Eggplant', 25, 'EUR');

INSERT INTO BUY_MULTI_PROMOTION (id, code, name, product_id, required_quantity, special_price_amount, special_price_currency_code) VALUES (1, 'MULTI-0001', 'Buy 3 apples for 70', 1, 3, 70, 'EUR');
INSERT INTO BUY_MULTI_PROMOTION (id, code, name, product_id, required_quantity, special_price_amount, special_price_currency_code) VALUES (2, 'MULTI-0002', 'Buy 2 beers for 15', 2, 2, 15, 'EUR');
INSERT INTO BUY_MULTI_PROMOTION (id, code, name, product_id, required_quantity, special_price_amount, special_price_currency_code) VALUES (3, 'MULTI-0003', 'Buy 4 for 60', 3, 4, 60, 'EUR');
INSERT INTO BUY_MULTI_PROMOTION (id, code, name, product_id, required_quantity, special_price_amount, special_price_currency_code) VALUES (4, 'MULTI-0004', 'Buy 2 eggplants for 40', 4, 2, 40, 'EUR');

INSERT INTO BUY_TOGETHER_PROMOTION (id, code, name, discount_amount, discount_currency_code) VALUES (1, 'TOG-0001', 'Buy Apple with Eggplant', 10, 'EUR');
INSERT INTO BUY_TOGETHER_PROMOTION (id, code, name, discount_amount, discount_currency_code) VALUES (2, 'TOG-0002', 'Buy Beer with Coca-Cola', 5, 'EUR');

INSERT INTO BUY_TOGETHER_PROMOTION_PRODUCT (buy_together_promotion_id, product_id) VALUES (1, 1);
INSERT INTO BUY_TOGETHER_PROMOTION_PRODUCT (buy_together_promotion_id, product_id) VALUES (1, 4);
INSERT INTO BUY_TOGETHER_PROMOTION_PRODUCT (buy_together_promotion_id, product_id) VALUES (2, 2);
INSERT INTO BUY_TOGETHER_PROMOTION_PRODUCT (buy_together_promotion_id, product_id) VALUES (2, 3);