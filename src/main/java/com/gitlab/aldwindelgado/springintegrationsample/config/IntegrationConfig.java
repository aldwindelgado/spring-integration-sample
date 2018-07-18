package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.ObjectToStringTransformer;
import org.springframework.messaging.Message;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public QueueChannel outputDtoChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel outputStringChannel() {
        return new QueueChannel(20);
    }


    @Bean(PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaulPoller() {
        PollerMetadata metadata = new PollerMetadata();
        metadata.setTrigger(new PeriodicTrigger(1000));
        metadata.setMaxMessagesPerPoll(5);
        metadata.setReceiveTimeout(5000);
        metadata.setSendTimeout(5000);
        return metadata;
    }

    @Transformer(inputChannel = "inputDtoChannel", outputChannel = "outputStringChannel")
    public Message<?> toStringTransformer(Message<SampleDTO> message) {
        return new ObjectToStringTransformer().transform(message);
    }
}
