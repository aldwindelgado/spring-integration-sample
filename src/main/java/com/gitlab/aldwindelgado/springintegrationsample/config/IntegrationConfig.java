package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.CustomMessageComparator;
import java.util.Comparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PriorityChannel;
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
    public PriorityChannel inputChannel() {
        return new PriorityChannel(15, customComparator());
    }

    @Bean
    public Comparator<Message<?>> customComparator() {
        return new CustomMessageComparator();
    }
}
