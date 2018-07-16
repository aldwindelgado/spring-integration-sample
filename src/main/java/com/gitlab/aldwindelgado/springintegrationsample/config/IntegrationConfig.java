package com.gitlab.aldwindelgado.springintegrationsample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.router.RecipientListRouter;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Configuration
@EnableIntegration
public class IntegrationConfig {

//    @Bean
//    public DirectChannel defaultChannel() {
//        return new DirectChannel();
//    }

    @Bean
    public DirectChannel inputChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "inputChannel")
    public RecipientListRouter router() {
        RecipientListRouter router = new RecipientListRouter();
//        router.setSendTimeout(1); // 1 second timeout
        router.setApplySequence(true); // send the headers to the channels downstream
        router.addRecipient("intChannel", "headers.containsKey('X-CUSTOM')");
//        router.addRecipient("intChannel");
        router.addRecipient("stringChannel");
        router.addRecipient("defaultChannel");
        router.setDefaultOutputChannelName("defaultChannel");
        return router;
    }

}
