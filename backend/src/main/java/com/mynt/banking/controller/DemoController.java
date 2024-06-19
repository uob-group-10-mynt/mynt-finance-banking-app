package com.mynt.banking.controller;


import com.mynt.banking.service.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DemoController {

  private final DemoService demoService;

  @GetMapping("")
  public String defalt(){
    return "happy day - original class !!!";
  }

  @GetMapping("/healthcheck")
  public String getHealthCheck() {
    return "Health Check";
  }

}
