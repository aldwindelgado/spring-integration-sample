package com.gitlab.aldwindelgado.springintegrationsample.gateway;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.Map;
import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PrintGateway {

    @Gateway(requestChannel = "inputChannel")
    Future<Message<SampleDTO>> printDTOString(Message<Map<Object, Object>> message);
}
