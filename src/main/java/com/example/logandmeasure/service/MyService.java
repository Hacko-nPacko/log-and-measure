package com.example.logandmeasure.service;

import com.example.logandmeasure.repository.MyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 6.02.18.
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MyService {

    private final MyRepository myRepository;

    public void doWork() {
        Object fetch = myRepository.fetch();
        log.debug("service fetched: {}", fetch);
    }

    public void doThrow() {
        myRepository.throwing();
    }
}

