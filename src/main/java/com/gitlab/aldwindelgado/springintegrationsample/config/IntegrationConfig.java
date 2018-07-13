package com.gitlab.aldwindelgado.springintegrationsample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.router.HeaderValueRouter;

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
    @ServiceActivator(inputChannel = "inputChannel")
    public HeaderValueRouter router() {
        HeaderValueRouter router = new HeaderValueRouter("X-ROUTER");
//        router.setResolutionRequired);
        router.setChannelMapping("to-int", "intChannel");
        router.setChannelMapping("to-string", "stringChannel");

        return router;
    }

}
