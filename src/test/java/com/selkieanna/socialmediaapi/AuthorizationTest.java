package com.selkieanna.socialmediaapi;

import com.selkieanna.socialmediaapi.authorization.controller.AuthController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorizationTest {

    @Autowired
    AuthController controller;
}
