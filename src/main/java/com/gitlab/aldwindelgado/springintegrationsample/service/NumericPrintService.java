package com.gitlab.aldwindelgado.springintegrationsample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@MessageEndpoint
public class NumericPrintService {

    @ServiceActivator(inputChannel = "intChannel")
    public Message<?> print(Message<?> message) {
        log.debug("[###] NUMBER Payload: {}", message.getPayload());
//        log.info("[###] Headers Params: {}", headers);
        int counterNumber = Integer.class.cast(message.getHeaders().get("X-COUNTER"));
        return MessageBuilder
            .withPayload(String.format("Sending a reply for %s", counterNumber))
            .build();
    }

}
