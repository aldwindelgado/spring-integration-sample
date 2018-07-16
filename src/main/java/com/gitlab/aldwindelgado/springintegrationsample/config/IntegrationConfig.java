package com.gitlab.aldwindelgado.springintegrationsample.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel outputChannel() {
        return new DirectChannel();
    }

    @Bean
    public DirectChannel aggregatorChannel() {
        return new DirectChannel();
    }

    @Splitter(inputChannel = "inputChannel", outputChannel = "aggregatorChannel")
    public List<String> splitter(Message<String> message) {
        return new ArrayList<>(Arrays.asList(message.getPayload().split(" ")));
    }

    @Aggregator(inputChannel = "aggregatorChannel", outputChannel = "outputChannel")
    public Message<?> aggregate(List<Message<String>> messages) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Message<String> message : messages) {
            log.info("[###] Aggregate Payload: {}", message.getPayload().toUpperCase());
            stringBuilder.append(message.getPayload());
            stringBuilder.append("+++");
        }

        log.info("[###] Built string: {}", stringBuilder.toString());
        return MessageBuilder.withPayload(stringBuilder.toString()).build();
    }

}
