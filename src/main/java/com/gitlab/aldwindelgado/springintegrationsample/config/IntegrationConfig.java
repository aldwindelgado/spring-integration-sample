package com.gitlab.aldwindelgado.springintegrationsample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public DirectChannel inputChannel() {
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(true);
        directChannel.setMaxSubscribers(2);
        return directChannel;
    }
}
