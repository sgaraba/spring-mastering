package com.sgaraba.springmastering.controller;

import com.sgaraba.springmastering.SpringMasteringApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringMasteringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerIntegrationTest {

    private static final String LOCAL_HOST = "http://localhost:";

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void welcome() {
        final ResponseEntity<String> response = template.getForEntity(createURL("/welcome"), String.class);
        assertThat(response.getBody(), equalTo("Hello World"));
    }

    @Test
    public void welcomeWithObject() {
        final ResponseEntity<String> response = template.getForEntity(createURL("/welcome-with-object"), String.class);
        assertThat(response.getBody(), containsString("Hello World"));
    }

    @Test
    public void welcomeWithParameter() {
        final ResponseEntity<String> response = template
                .getForEntity(createURL("/welcome-with-parameter/name/Buddy"), String.class);
        assertThat(response.getBody(), containsString("Hello World, Buddy!"));
    }

    private String createURL(String uri) {
        return String.format("%s%s%s", LOCAL_HOST, port, uri);
    }
}
