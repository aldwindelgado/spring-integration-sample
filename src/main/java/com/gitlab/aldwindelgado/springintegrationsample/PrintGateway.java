package com.gitlab.aldwindelgado.springintegrationsample;

import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

@MessagingGateway(defaultRequestChannel = "pollableChannel")
public interface PrintGateway {

    @Gateway(requestChannel = "pollableChannel")
    Future<Message<String>> print(Message<?> message);
}
