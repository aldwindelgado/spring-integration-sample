package com.gitlab.aldwindelgado.springintegrationsample.service;

import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Service
public class PrintService {


    @ServiceActivator(inputChannel = "directInputChannel")
    public Message<?> print(Message<String> message) {
        log.info("[###] Message from print service: {}", message.getPayload());

        MessageHeaders headers = message.getHeaders();

        for (Entry<String, Object> entry : headers.entrySet()) {
            log.info("[###] Key: {}", entry.getKey());
            log.info("[###] Value: {}", entry.getValue());
        }

        log.info("[###] Payload: {}", message.getPayload());
        return MessageBuilder.withPayload("This is the updated message").build();
    }

}
