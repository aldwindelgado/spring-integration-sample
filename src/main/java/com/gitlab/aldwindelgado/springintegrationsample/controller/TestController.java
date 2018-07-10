package com.gitlab.aldwindelgado.springintegrationsample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public ResponseEntity<Void> testSample() {
        log.info("[###] The endpoint is working");

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
