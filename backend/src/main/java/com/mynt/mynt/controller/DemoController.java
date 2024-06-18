package com.mynt.mynt.controller;


import com.mynt.mynt.service.DemoService;
import lombok.AllArgsConstructor;
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
    return "happy day!!!";
  }


  @GetMapping("/healthcheck")
  public String getHealthCheck() {
    return "Health Check";
  }

}
