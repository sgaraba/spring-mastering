package com.sgaraba.springmastering.controller;

import com.sgaraba.springmastering.SpringMasteringApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringMasteringApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BasicControllerIntegrationTest {

    private static final String LOCAL_HOST = "http://localhost:";
    

}
