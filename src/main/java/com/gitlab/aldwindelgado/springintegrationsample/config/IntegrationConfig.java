package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.http.inbound.HttpRequestHandlingMessagingGateway;
import org.springframework.integration.http.inbound.RequestMapping;
import org.springframework.integration.http.outbound.HttpRequestExecutingMessageHandler;
import org.springframework.integration.json.ObjectToJsonTransformer;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.support.PeriodicTrigger;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    private final String URL = "http://localhost:9090/testing";

    @Bean
    public MessageChannel inputChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageChannel outputChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageChannel httpRequestChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageChannel httpOutboundChannel() {
        return new QueueChannel(10);
    }

    @Bean
    public MessageChannel transformerChannel() {
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
    public HttpRequestHandlingMessagingGateway inboundGateway() {
        log.info("[###] INBOUND GATEWAY");
        HttpRequestHandlingMessagingGateway gateway = new HttpRequestHandlingMessagingGateway();
        gateway.setRequestMapping(postMapping());
        gateway.setRequestPayloadType(ResolvableType.forClass(SampleDTO.class));
        gateway.setRequestChannel(httpRequestChannel());
        return gateway;
    }

    @Bean
    public RequestMapping postMapping() {
        log.info("[###] POST MAPPING");
        RequestMapping requestMapping = new RequestMapping();
        requestMapping.setPathPatterns("/testing");
        requestMapping.setMethods(HttpMethod.POST);
        requestMapping.setProduces(MediaType.APPLICATION_JSON_UTF8_VALUE);
        requestMapping.setConsumes(MediaType.APPLICATION_JSON_UTF8_VALUE);
        return requestMapping;
    }

    @Bean
    @ServiceActivator(inputChannel = "httpOutboundChannel")
    public HttpRequestExecutingMessageHandler outboundGateway() {
        log.info("[###] OUTBOUND GATEWAY: {}", URL);
        HttpRequestExecutingMessageHandler handler = new HttpRequestExecutingMessageHandler(URL);
        handler.setHttpMethod(HttpMethod.POST);
        handler.setExpectedResponseType(SampleDTO.class);
        return handler;
    }

    // TODO: Create a shitty transformer here
    // Refer to: https://stackoverflow.com/questions/20083604/spring-integration-how-to-pass-post-request-parameters-to-http-outbound?rq=1
    @Transformer(inputChannel = "transformerChannel", outputChannel = "httpOutboundChannel")
    public Message<?> toJsonStringTransformer(Message<SampleDTO> message) {
        log.info("[###] TRANSFORMER: {}", message);
        return new ObjectToJsonTransformer().transform(message);
    }
}
