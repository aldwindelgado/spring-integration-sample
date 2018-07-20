package com.gitlab.aldwindelgado.springintegrationsample.service;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.List;
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
    public SampleDTO printDto(SampleDTO sampleDTO) {
        SampleDTO sampleDTO1 = sampleDTO;
        sampleDTO1.setFullName(sampleDTO.getFullName().toUpperCase());

        return sampleDTO1;
    }

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public SampleDTO evaluateSampleDto(SampleDTO sampleDTO) {

        SampleDTO sampleDTO1 = new SampleDTO();
        sampleDTO1.setVersion(sampleDTO.getVersion());
        sampleDTO1.setFullName(
            String.format("%s, %s", sampleDTO.getLastName(), sampleDTO.getFirstName()));

        return sampleDTO1;
    }

//    @ServiceActivator(inputChannel = "httpRequestChannel")
//    public Message<SampleDTO> httpRequestChannel(Message<SampleDTO> message) {
//        log.info("[###] HTTP REQUEST CHANNEL: {}", message);
//
//        String firstName = message.getPayload().getFirstName().toLowerCase();
//        String lastName = message.getPayload().getLastName().toLowerCase();
//
//        String capitalizedFirstName = StringUtils.capitalize(firstName);
//        String capitalizedLastName = StringUtils.capitalize(lastName);
//
//        String fullName = String
//            .format("%s, %s", StringUtils.capitalize(lastName), StringUtils.capitalize(firstName));
//        Integer version =
//            message.getPayload().getVersion() == null ? 1 : message.getPayload().getVersion() + 10;
//
//        return MessageBuilder
//            .withPayload(
//                new SampleDTO(capitalizedFirstName, capitalizedLastName, fullName, version))
//            .build();
//    }

    @ServiceActivator(inputChannel = "httpRequestChannel")
    public Message<?> httpRequestChannel(Message<SampleDTO> message) {
        log.info("[###] HTTP REQUEST CHANNEL: {}", message);
        return MessageBuilder.fromMessage(message).build();
    }
}
