package com.gitlab.aldwindelgado.springintegrationsample;

import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "inputChannel")
public interface PrintGateway {

    @Gateway(requestChannel = "inputChannel")
    Future<Message<String>> print(Message<?> message);
}
