package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.HeaderFilter;
import org.springframework.integration.transformer.MapToObjectTransformer;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.StaticHeaderValueMessageProcessor;
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
    public QueueChannel inputChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel filterChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel enrichChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel outputChannel() {
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

    @Transformer(inputChannel = "inputChannel", outputChannel = "filterChannel")
    public Message<?> toDtoTransformer(Message<Map<Object, Object>> message) {
        return new MapToObjectTransformer(SampleDTO.class).transform(message);
    }

    @Transformer(inputChannel = "filterChannel", outputChannel = "enrichChannel")
    public Message<?> filterHeader(Message<Map<Object, Object>> message) {
        return new HeaderFilter("X-HEADER", "X-HEADER-2").transform(message);
    }

    @Transformer(inputChannel = "enrichChannel", outputChannel = "outputChannel")
    public Message<?> enrichHeader(Message<Map<Object, Object>> message) {
        Map<String, HeaderValueMessageProcessor<?>> headersToAdd = new HashMap<>();
        headersToAdd.put("A-BCD",
            new StaticHeaderValueMessageProcessor<>("The value of this enrich header"));
        headersToAdd.put("E-FGH",
            new StaticHeaderValueMessageProcessor<>("This is the shit!"));
        headersToAdd.put("I-JKL",
            new StaticHeaderValueMessageProcessor<>("The shit"));
        return new HeaderEnricher(headersToAdd).transform(message);
    }

}
