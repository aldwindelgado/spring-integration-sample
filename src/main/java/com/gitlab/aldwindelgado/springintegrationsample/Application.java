package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.service.PrintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessagingTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    private final PrintService printService;
    private final DirectChannel directInputChannel;

    public Application(PrintService printService,
        DirectChannel directInputChannel) {
        this.printService = printService;
        this.directInputChannel = directInputChannel;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

//        directOutputChannel
//            .subscribe(messageHandler -> log.info("[###] {}", messageHandler.getPayload()));

        Message<String> message = MessageBuilder.withPayload("Hello there! :)")
            .setHeader("X-Key", "X-Value").build();

//        log.info("[###] {}", message);
//        directInputChannel.send(message);

        MessagingTemplate template = new MessagingTemplate();
        Message returnedMessage = template.sendAndReceive(directInputChannel, message);
        log.info("[###] Returned message: {}", returnedMessage);
    }
}
