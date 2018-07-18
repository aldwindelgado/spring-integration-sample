package com.gitlab.aldwindelgado.springintegrationsample.gateway;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "inputDtoChannel")
public interface PrintGateway {

    @Gateway(requestChannel = "inputDtoChannel")
    Future<Message<String>> printDTOString(Message<SampleDTO> message);
}
