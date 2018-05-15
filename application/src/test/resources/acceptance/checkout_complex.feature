Feature: Checkout complex usages
  Scenario: two users are shopping at the supermarket
    When the user open checkout no.1
    When the user start handling new basket in checkout no.1

    When the user open checkout no.5
    When the user start handling new basket in checkout no.5

    When the user scan product 123456789012 in checkout no.1
    Then the user receives actual price: 40,00 EUR
    When the user scan product 123456789012 in checkout no.1
    Then the user receives actual price: 80,00 EUR

    When the user scan product 213622236041 in checkout no.5
    Then the user receives actual price: 30,00 EUR
    When the user scan product 213622236041 in checkout no.5
    Then the user receives actual price: 60,00 EUR

    When the user pay for basket in checkout no.1
    Then the user receives receipt with: Apple 2 40,00 EUR 80,00 EUR
    Then the user receives receipt with: TOTAL: 80,00 EUR

    When the user pay for basket in checkout no.5
    Then the user receives receipt with: Coca-Cola 2 30,00 EUR 60,00 EUR
    Then the user receives receipt with: TOTAL: 60,00 EUR

    When the user close checkout no.1
    When the user close checkout no.5

  Scenario: user is doing a big shopping
    When the user open checkout no.3
    When the user start handling new basket in checkout no.3

    When the user scan product 123456789012 in checkout no.3
    Then the user receives actual price: 40,00 EUR
    When the user scan product 836430990129 in checkout no.3
    Then the user receives actual price: 50,00 EUR
    When the user scan product 836430990129 in checkout no.3
    Then the user receives actual price: 55,00 EUR
    When the user scan product 213622236041 in checkout no.3
    Then the user receives actual price: 80,00 EUR
    When the user scan product 725272730701 in checkout no.3
    Then the user receives actual price: 95,00 EUR

    When the user pay for basket in checkout no.3
    Then the user receives receipt with: Apple 1 40,00 EUR 40,00 EUR
    Then the user receives receipt with: Beer (Buy 2 beers for 15) 1 15,00 EUR 15,00 EUR
    Then the user receives receipt with: Coca-Cola 1 30,00 EUR 30,00 EUR
    Then the user receives receipt with: Eggplant 1 25,00 EUR 25,00 EUR
    Then the user receives receipt with: Discounts:
    Then the user receives receipt with: Buy Beer with Coca-Cola -5,00 EUR
    Then the user receives receipt with: Buy Apple with Eggplant -10,00 EUR
    Then the user receives receipt with: TOTAL: 95,00 EUR

    When the user close checkout no.3