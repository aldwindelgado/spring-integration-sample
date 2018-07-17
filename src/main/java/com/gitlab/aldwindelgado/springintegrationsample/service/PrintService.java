package com.gitlab.aldwindelgado.springintegrationsample.service;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
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
public class PrintService {

    @ServiceActivator(inputChannel = "outputDtoChannel")
    public Message<String> print(Message<SampleDTO> message) {
//        log.info("[###] Version: {}", version);
        log.info("[###] RECEIVED Message DTO: {}", message.getPayload());
        log.info("[###] RECEIVED Message Headers DTO: {}", message.getHeaders());
        return MessageBuilder
            .withPayload(message.getPayload().toString())
            .build();
    }

    @ServiceActivator(inputChannel = "discardDtoChannel")
    public Message<String> printDiscard(Message<SampleDTO> message) {
        log.info("[###] DISCARDED Message DTO: {}", message.getPayload());
        log.info("[###] DISCARDED Message Headers DTO: {}", message.getHeaders());
        return MessageBuilder
            .withPayload(message.getPayload().toString())
            .build();
    }

}
