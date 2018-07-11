package com.gitlab.aldwindelgado.springintegrationsample;

import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "queueInputChannel")
public interface PrintGateway {

    @Gateway(requestChannel = "queueInputChannel")
    Future<Message<String>> print(Message<?> message);
}
