package com.gitlab.aldwindelgado.springintegrationsample.service;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@MessageEndpoint
public class PrintService {

    @ServiceActivator(inputChannel = "outputChannel")
    public Message<?> print(Message<?> message, @Headers Map<String, Object> headers) {
        log.debug("[###] Payload: {}", message.getPayload());
        return MessageBuilder
            .withPayload(String.format("Sending a reply for %s", message.getPayload()))
            .build();
    }

}
