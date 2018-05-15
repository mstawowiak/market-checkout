# Market Checkout

## Business requirements:
1. Design and implement market checkout component with readable API that calculates the total price of a number of items.
2. Checkout mechanism can scan items and return actual price (is stateful).
3. Our goods are priced individually.
4. Some items are multi-priced: buy N of them, and they’ll cost you Y cents.

| Item  | Price | Unit  | Special Price |
|:-----:|------:| -----:|:-------------:|
| A     | 40    | 3     | for 70        |
| B     | 10    | 2     | for 15        |
| C     | 30    | 4     | for 60        |
| D     | 25    | 2     | for 40        |

5. Client receives receipt containing list of all products with corresponding prices after payment.
6. Some items are cheaper when bought together - buy item X with item Y and save Z cents.

## Design
Market Checkout is designed using Domain-Driven-Design approach. Most of the business logic is contained in domain objects around one bounded context. Communication with boundaries (database, REST services) is separated by using interfaces.

Application is divided into 3 modules:

* **domain** - contains entities, value objects, repository interfaces and factories responsible for core business logic
* **infrastructure** - contains implementation of repositories which communicates with database
* **application** - contains REST API and Service for handling interactions with user

### Main classess:
* **Basket** - the most important domain object. Stores all products, apply promotions and calculate total cost
* **Checkout** - cash register in market which can handle baskets
* **Product** - goods which can be bought in market
* **BuyMultiPromotion** - promotion of multi-priced products
* **BuyTogetherPromotion** - promotion for products which are cheaper when bought together
* **ReceiptPrinter** - interface for printing receipt of paid Basket, project include one default implementation: **DefaultReceiptPrinter**
* **MarketCheckoutRestApi** - controller for handle REST requests
* **MarketCheckoutService** with **MarketCheckoutServiceImpl** - stateful service which keep state of open checkouts and delegate actions to domain objects

## Assumptions
- market can handle multiple checkouts
- checkouts can be opened and closed
- before scanning product checkout must be opened and have basket assigned
- checkout can handle one basket at one time
- product are scaned one by one
- products and promotions are already defined in database
- application uses in-memory database, which is initialized with some data during application startup (from file: src/main/resources/data.sql)
- all promotions in database are active so all of them can be applied to products
- for product can exists only one "buy multi" promotion (for simplification)
- discount for buying things together is included only once, regardless of the number of purchased promotional products
- baskets are not saved in database (but it could be, ex. after successful payment)

## API
```
POST   /api/v1/checkouts/{checkoutNo} - open checkout
DELETE /api/v1/checkouts/{checkoutNo} - close checkout

POST   /api/v1/checkouts/{checkoutNo}/basket - start handle basket in specified checkout
PUT    /api/v1/checkouts/{checkoutNo}/basket/scan/{productCode} - scan product in specified checkout and return current total cost
PUT    /api/v1/checkouts/{checkoutNo}/basket/pay - pay for basket and return readable receipt
```

## Build

```
mvn package
```
Executable jar will be generated in path: _application/target/market-checkout-1.0.0-spring-boot.jar_

## Tests

Project includes three types of tests:
* **unit** - implemented using JUnit library
* **integration** - tests of integration with DB (using H2 in-memory databse and Spring Data JPA Test) and tests of REST API (using Spring MVC Test)
* **acceptance** - implemented using Cucumber in BDD aproach 

```
unit:         mvn test
integration:  mvn verify
acceptance:   mvn verify -P acceptance
```

## Run

Application is packaged as executable jar and can be run "in-place".

```
java -jar market-checkout-1.0.0.jar
```

During bootstrap application initalize in-memory H2 database and start embedded Tomcat on port 8080.

REST API is then served on address: [http://localhost:8080/api/v1/checkouts](http://localhost:8080/api/v1/checkouts)

### Example usage of REST API

```
POST   http://localhost:8080/api/v1/checkouts/1
POST   http://localhost:8080/api/v1/checkouts/1/basket
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/123456789012
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/836430990129
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/836430990129
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/836430990129
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/213622236041
PUT    http://localhost:8080/api/v1/checkouts/1/basket/scan/725272730701
PUT    http://localhost:8080/api/v1/checkouts/1/basket/pay
DELETE http://localhost:8080/api/v1/checkouts/1
```