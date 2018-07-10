package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    private final PrintService printService;
    private final DirectChannel directChannel;

    public Application(PrintService printService,
        DirectChannel directChannel) {
        this.printService = printService;
        this.directChannel = directChannel;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        directChannel
            .subscribe(messageHandler -> printService.print((Message<String>) messageHandler));

        Message<String> message = MessageBuilder.withPayload("Hello there! :)")
            .setHeader("X-Key", "X-Value").build();

        log.info("[###] {}", message);
        directChannel.send(message);
    }
}
