Feature: Checkout promotions
  Scenario: user buy multi-priced product
    When the user open checkout no.1
    When the user start handling new basket in checkout no.1

    When the user scan product 123456789012 in checkout no.1
    Then the user receives actual price: 40,00 EUR
    When the user scan product 123456789012 in checkout no.1
    Then the user receives actual price: 80,00 EUR
    When the user scan product 123456789012 in checkout no.1
    Then the user receives actual price: 70,00 EUR

    When the user pay for basket in checkout no.1
    Then the user receives receipt with: Apple (Buy 3 apples for 70) 1 70,00 EUR 70,00 EUR
    Then the user receives receipt with: TOTAL: 70,00 EUR

    When the user close checkout no.1

  Scenario: user buy products with buy-together promotion
    When the user open checkout no.2
    When the user start handling new basket in checkout no.2

    When the user scan product 836430990129 in checkout no.2
    Then the user receives actual price: 10,00 EUR
    When the user scan product 213622236041 in checkout no.2
    Then the user receives actual price: 35,00 EUR

    When the user pay for basket in checkout no.2
    Then the user receives receipt with: Beer 1 10,00 EUR 10,00 EUR
    Then the user receives receipt with: Coca-Cola 1 30,00 EUR 30,00 EUR
    Then the user receives receipt with: Discounts:
    Then the user receives receipt with: Buy Beer with Coca-Cola -5,00 EUR
    Then the user receives receipt with: TOTAL: 35,00 EUR

    When the user close checkout no.2