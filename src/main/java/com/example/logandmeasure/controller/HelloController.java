package com.example.logandmeasure.controller;

import com.example.logandmeasure.Measure;
import com.example.logandmeasure.service.MyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created on 6.02.18.
 */
@Slf4j
@RestController
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class HelloController {

    private final MyService myService;

    private final Measure measure;

    @GetMapping("/")
    public String index() {
        log.debug("we're serving a GET now");
        measure.inc("an.index.counter");
        myService.doWork();
        return "done";
    }

    @GetMapping("/throwing")
    public String throwing() {
        log.debug("we're serving a GET now");
        myService.doThrow();
        return "done";
    }
}
