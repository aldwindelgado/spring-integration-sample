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

    @ServiceActivator(inputChannel = "outputChannel")
    public Message<SampleDTO> printJsonToDto(Message<SampleDTO> message) {
        log.info("[###] Sending reply message DTO: {}", message.getPayload());
        log.info("[###] Sending reply message Headers DTO: {}", message.getHeaders());

        return MessageBuilder
            .withPayload(message.getPayload())
            .build();
    }

}
