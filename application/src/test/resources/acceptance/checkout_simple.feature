Feature: Checkout simple usages
  Scenario: user open and close checkout
    When the user open checkout no.1
    Then the user receives success status code

    When the user close checkout no.1
    Then the user receives success status code

  Scenario: user scan and buy one product
    When the user open checkout no.1
    Then the user receives success status code

    When the user start handling new basket in checkout no.1
    Then the user receives success status code

    When the user scan product 123456789012 in checkout no.1
    Then the user receives success status code
    Then the user receives actual price: 40,00 EUR

    When the user pay for basket in checkout no.1
    Then the user receives success status code
    Then the user receives receipt with: Apple 1 40,00 EUR 40,00 EUR
    Then the user receives receipt with: TOTAL: 40,00 EUR

    When the user close checkout no.1
    Then the user receives success status code