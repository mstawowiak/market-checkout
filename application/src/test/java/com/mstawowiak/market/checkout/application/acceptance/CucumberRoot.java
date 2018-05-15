package com.mstawowiak.market.checkout.application.acceptance;

import com.mstawowiak.market.checkout.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
public class CucumberRoot {

    @Autowired
    protected TestRestTemplate template;

    protected <T> ResponseEntity<T> post(String url, Class<T> responseType) {
        return template.postForEntity(url, null, responseType);
    }

    protected <T> ResponseEntity<T> put(String url, Class<T> responseType) {
        return template.exchange(url, HttpMethod.PUT, null, responseType);
    }

    protected <T> ResponseEntity<T> delete(String url, Class<T> responseType) {
        return template.exchange(url, HttpMethod.DELETE, null, responseType);
    }

}
