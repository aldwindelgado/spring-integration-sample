package com.gitlab.aldwindelgado.springintegrationsample.service;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Order(1)
@MessageEndpoint
public class PrintService {


    @ServiceActivator(inputChannel = "inputChannel")
    public Message<?> print(Message<String> message, @Headers Map<String, Object> headers) {
        throw new RuntimeException("This is an error from print service");
//        log.info("[###] Payload: {}", message.getPayload());
////        log.info("[###] Headers Params: {}", headers);
//        int messageNumber = Integer.class.cast(message.getHeaders().get("X-MESSAGE-NUMBER"));
//        int counterNumber = Integer.class.cast(message.getHeaders().get("X-COUNTER"));
//        return MessageBuilder
//            .withPayload(String
//                .format("Sending a reply for %s with message number of %s", counterNumber,
//                    messageNumber))
//            .build();
    }

}
