package com.gitlab.aldwindelgado.springintegrationsample.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Splitter;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
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

    @Splitter(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public List<String> splitter(Message<String> message) {
        return new ArrayList<>(Arrays.asList(message.getPayload().split(" ")));
    }

}
