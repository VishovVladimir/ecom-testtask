package com.ecom.task.controllers;

import com.ecom.task.common.limitter.RateLimited;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

    @GetMapping("/task")
    @RateLimited
    public void getTask() {

    }
}
