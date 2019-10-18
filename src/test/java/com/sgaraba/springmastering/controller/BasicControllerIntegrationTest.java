package com.sgaraba.springmastering.controller;

import com.sgaraba.springmastering.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static com.sgaraba.springmastering.utils.TestUtils.createHeaders;
import static com.sgaraba.springmastering.utils.TestUtils.createURL;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerIntegrationTest {

    @LocalServerPort
    private int port;

    private TestRestTemplate template = new TestRestTemplate();

    @Test
    public void welcome() {
        ResponseEntity<String> response = template.getForEntity(createURL("/welcome", port), String.class);
        assertThat(response.getBody(), equalTo("Hello World"));
    }

    @Test
    public void welcomeWithObject() {
        ResponseEntity<String> response = template.exchange(createURL("/welcome-with-object", port),
                HttpMethod.GET, new HttpEntity<String>(null, createHeaders("admin", "password")), String.class);
        assertThat(response.getBody(), containsString("Hello World"));
    }

    @Test
    public void welcomeWithParameter() {
        ResponseEntity<String> response = template
                .getForEntity(createURL("/welcome-with-parameter/name/Buddy", port), String.class);
        assertThat(response.getBody(), containsString("Hello World, Buddy!"));
    }
}
