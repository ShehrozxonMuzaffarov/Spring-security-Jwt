package com.apprestjwt.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ReportController {
    @GetMapping("/report")
    public String home(){
        return "welcome to Report PAge";
    }
}
