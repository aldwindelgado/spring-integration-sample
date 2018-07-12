package com.gitlab.aldwindelgado.springintegrationsample.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Order(2)
@MessageEndpoint
public class UppercasePrintService {


    @ServiceActivator(inputChannel = "inputChannel")
    public Message<?> print(Message<String> message) {
        log.info("[###] UC Payload: {}", message.getPayload().toUpperCase());
//        log.info("[###] Headers Params: {}", headers);
        int messageNumber = Integer.class.cast(message.getHeaders().get("X-MESSAGE-NUMBER"));
        int counterNumber = Integer.class.cast(message.getHeaders().get("X-COUNTER"));
        return MessageBuilder
            .withPayload(String
                .format("Sending a reply for %s with message number of %s", counterNumber,
                    messageNumber))
            .build();
    }

}
