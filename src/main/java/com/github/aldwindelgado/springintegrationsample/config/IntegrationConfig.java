package com.github.aldwindelgado.springintegrationsample.config;

import com.github.aldwindelgado.springintegrationsample.domain.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.aggregator.MessageCountReleaseStrategy;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final String TEST_URL = "http://localhost:7777/testing";

    @Bean
    public MessageChannel inputChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new QueueChannel(10);
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

    @Bean
    public IntegrationFlow outboundGatewayFlow() {
        return IntegrationFlows
            .from("inputChannel")
            .log(Level.INFO, message -> message)
            .aggregate(aggregatorSpec -> aggregatorSpec
                .releaseStrategy(new MessageCountReleaseStrategy(3))
                .correlationStrategy(
                    message -> SampleDTO.class.cast(message.getPayload()).getVersion()
                )
                .expireGroupsUponTimeout(true)
                .groupTimeout(5000)
            )
            .transform(Transformers.toJson())
            .handle(Http.outboundGateway(TEST_URL)
                .httpMethod(HttpMethod.POST)
                .expectedResponseType(SampleDTO.class)
            )
            .get();
    }

    @Bean
    public IntegrationFlow inboundGatewayFlow() {
        return IntegrationFlows
            .from(Http.inboundGateway("/testing")
                .requestPayloadType(SampleDTO[].class)
                .requestMapping(requestMappingSpec -> requestMappingSpec
                    .methods(HttpMethod.POST)
                    .consumes(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .produces(MediaType.APPLICATION_JSON_UTF8_VALUE)
                )
            )
            .log(Level.INFO, message -> message)
            .transform(Transformers.toJson())
            .channel("inboundChannel")
            .get();
    }

    @Bean
    public IntegrationFlow inboundChannelFlow() {
        return IntegrationFlows
            .from("inboundChannel")
            .log(Level.INFO, message -> message.getPayload())
            .get();
    }
}
