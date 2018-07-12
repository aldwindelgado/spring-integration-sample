package com.gitlab.aldwindelgado.springintegrationsample.service;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Service
public class PrintService {


    @ServiceActivator(inputChannel = "inputChannel",
        poller = @Poller(fixedRate = "5000", maxMessagesPerPoll = "4"))
    public Message<?> print(Message<String> message, @Headers Map<String, Object> headers) {
        log.info("[###] Payload: {}", message.getPayload());
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
