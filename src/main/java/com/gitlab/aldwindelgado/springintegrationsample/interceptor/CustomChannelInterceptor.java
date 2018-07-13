package com.gitlab.aldwindelgado.springintegrationsample.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.ChannelInterceptor;

/**
 * @author Aldwin Delgado
 */
@Slf4j
public class CustomChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
//        Message<String> inteceptedMessage = MessageBuilder
//            .withPayload(message.getPayload().toString() + " [Intercepted Message]").build();
//        return inteceptedMessage;
        log.info("[### Inteceptor");
        return MessageBuilder
            .withPayload(message.getPayload().toString() + " [Intercepted Message]").build();
    }


}
