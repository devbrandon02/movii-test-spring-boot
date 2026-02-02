package com.movii.test.movii_test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/keep-alive")
public class KeepAliveController {

    @GetMapping
    public String keepAlive() {
        return "Application is running";
    }
}