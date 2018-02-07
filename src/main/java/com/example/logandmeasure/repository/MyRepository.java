package com.example.logandmeasure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created on 6.02.18.
 */
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class MyRepository {

    public Object fetch() {
        log.debug("log from the repository");
        return new Object();
    }

    public void throwing() {
        throw new RuntimeException("throwing an exception");
    }
}
