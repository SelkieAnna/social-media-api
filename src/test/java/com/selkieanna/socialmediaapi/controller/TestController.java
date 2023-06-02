package com.selkieanna.socialmediaapi.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content";
    }

    @GetMapping("/authorized")
    @PreAuthorize("")
    public String authorizedAccess() {
        return "Private Content";
    }
}
