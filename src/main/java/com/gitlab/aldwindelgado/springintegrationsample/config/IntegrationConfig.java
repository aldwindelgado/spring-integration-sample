package com.gitlab.aldwindelgado.springintegrationsample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;

/**
 * @author Aldwin Delgado
 */
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public DirectChannel channel() {
        return new DirectChannel();
    }
}
