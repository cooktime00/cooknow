package com.side.cooktime.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Log4j2
@Controller
public class ServerCheckController {
    @GetMapping("/health")
    @ResponseBody
    public String healthCheck(){
        log.info("Server Status Check");
        return "Server is running. Time is "+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일, HH시 mm분 ss초"));
    }
}
