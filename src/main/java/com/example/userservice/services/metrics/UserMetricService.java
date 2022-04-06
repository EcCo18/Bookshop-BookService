package com.example.userservice.services.metrics;

import com.example.userservice.model.User;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserMetricService {

    private Counter userCreatedCounter;
    private Counter userReceivedCounter;
    private final MeterRegistry meterRegistry;

    @Autowired
    public UserMetricService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        initUserCounters();
    }

    private void initUserCounters() {
        log.info("init all user counters");
        userCreatedCounter = Counter.builder("users.created")
                .description("Number of users created")
                .register(meterRegistry);
        userReceivedCounter = Counter.builder("users.received")
                .description("Number of times all users were received")
                .register(meterRegistry);
    }

    public void processCreation(final User createdUser) {
        log.debug("incrementing user created counter, then returning createdUserObject: " + createdUser);
        userCreatedCounter.increment();
    }

    public void processReceived() {
        log.debug("incrementing all users received counter");
        userReceivedCounter.increment();
    }
}
