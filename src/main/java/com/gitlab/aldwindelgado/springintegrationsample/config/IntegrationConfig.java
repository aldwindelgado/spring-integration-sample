package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Aggregator;
import org.springframework.integration.annotation.CorrelationStrategy;
import org.springframework.integration.annotation.ReleaseStrategy;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.support.MessageBuilder;
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
    public QueueChannel inputDtoChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel outputDtoChannel() {
        return new QueueChannel(20);
    }

    @Bean
    public QueueChannel discardDtoChannel() {
        return new QueueChannel(20);
    }

    @Bean(PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata defaulPoller() {
        PollerMetadata metadata = new PollerMetadata();
        metadata.setTrigger(new PeriodicTrigger(2000));
        metadata.setMaxMessagesPerPoll(5);
        return metadata;
    }

    @Aggregator(inputChannel = "inputDtoChannel", outputChannel = "outputDtoChannel", discardChannel = "discardDtoChannel")
    public Message<List<SampleDTO>> aggregateDto(List<SampleDTO> dtos) {
        List<SampleDTO> sampleDtos = new ArrayList<>();
        for (SampleDTO sampleDto : dtos) {
            log.info("[###] AGGREGATOR: {}", sampleDto);
            sampleDtos.add(sampleDto);
        }

        return MessageBuilder.withPayload(sampleDtos).build();
    }

    @ReleaseStrategy
    public boolean canBeRelease(List<SampleDTO> dtos) {
        log.info("[###] RELEASE STRAT: {}", dtos);
        return dtos.size() == 2;
    }

    @CorrelationStrategy
    public String groupByVersion(SampleDTO dto) {
        log.info("[###] CORRELATION STRAT: {}", dto);
        return dto.getVersion().toString();
    }
}
