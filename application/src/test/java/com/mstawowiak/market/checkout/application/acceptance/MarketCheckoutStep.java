package com.mstawowiak.market.checkout.application.acceptance;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class MarketCheckoutStep extends CucumberRoot {

    private ResponseEntity<String> response;

    @When("^the user open checkout no.(\\d+)$")
    public void the_user_open_checkout(int checkoutNo) {
        response = post("/api/v1/checkouts/" + checkoutNo, String.class);
    }

    @When("^the user close checkout no.(\\d+)$")
    public void the_user_close_checkout(int checkoutNo) throws Throwable {
        response = delete("/api/v1/checkouts/" + checkoutNo, String.class);
    }

    @When("^the user start handling new basket in checkout no.(\\d+)$")
    public void the_user_start_handling_new_basket_in_checkout(int checkoutNo) throws Throwable {
        response = post("/api/v1/checkouts/" + checkoutNo + "/basket", String.class);
    }

    @When("^the user scan product (.*) in checkout no.(\\d+)$")
    public void the_user_scan_product(String productCode, int checkoutNo) {
        response = put("/api/v1/checkouts/" + checkoutNo + "/basket/scan/" + productCode, String.class);
    }

    @When("^the user pay for basket in checkout no.(\\d+)$")
    public void the_user_scan_product(int checkoutNo) {
        response = put("/api/v1/checkouts/" + checkoutNo + "/basket/pay", String.class);
    }

    @Then("^the user receives success status code$")
    public void the_user_receives_success_status_code() throws Throwable {
        HttpStatus currentStatusCode = response.getStatusCode();
        assertThat("status code is incorrect : " + response.getBody(),
                currentStatusCode.value(), is(200));
    }

    @Then("^the user receives actual price: (.*)$")
    public void the_user_receives_actual_price(String actualPrice) throws Throwable {
        String body = response.getBody();
        assertThat("actual price is incorrect : " + body, body, is(actualPrice));
    }

    @Then("^the user receives receipt with: (.*)$")
    public void the_user_receives_receipt_with(String receiptLine) throws Throwable {
        String body = response.getBody();
        assertTrue("receipt not contains line: " + receiptLine, containsIgnoringWhiteSpaces(body, receiptLine));
    }

    private static boolean containsIgnoringWhiteSpaces(String base, String toFind) {
        return base.replaceAll("\\s+","").contains(toFind.replaceAll("\\s+",""));
    }

}
