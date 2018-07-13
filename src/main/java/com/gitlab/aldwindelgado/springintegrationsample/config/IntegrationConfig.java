package com.gitlab.aldwindelgado.springintegrationsample.config;

import com.gitlab.aldwindelgado.springintegrationsample.interceptor.CustomChannelInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.BridgeFrom;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.channel.PriorityChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

    @Bean
    public PriorityChannel pollableChannel() {
        QueueChannel queueChannel = new QueueChannel();
        queueChannel.addInterceptor(channelInterceptor());
        return new PriorityChannel(10);
    }

    @Bean
    @BridgeFrom(value = "pollableChannel", poller = @Poller(fixedDelay = "5000", maxMessagesPerPoll = "2"))
    public PublishSubscribeChannel inputChannel() {
        return new PublishSubscribeChannel(customTaskExecutor());
    }

    @Bean
    public TaskExecutor customTaskExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        return executor;
    }

    @Bean
    @GlobalChannelInterceptor
    public ChannelInterceptor channelInterceptor() {
        return new CustomChannelInterceptor();
    }
}
