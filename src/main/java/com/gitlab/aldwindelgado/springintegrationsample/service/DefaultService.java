package com.gitlab.aldwindelgado.springintegrationsample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@MessageEndpoint
public class DefaultService {

    @ServiceActivator(inputChannel = "defaultChannel")
    public Message<?> print(Message<?> message) {
        log.debug("[###] DEFAULT channel: {}", message.getPayload());

        return MessageBuilder
            .withPayload("Replying from default channel")
            .build();

    }
}
