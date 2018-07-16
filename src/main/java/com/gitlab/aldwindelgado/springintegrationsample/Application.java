package com.gitlab.aldwindelgado.springintegrationsample;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    private final PrintGateway printGateway;

    public Application(PrintGateway printGateway) {
        this.printGateway = printGateway;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        for (int i = 0; i < 10; i++) {
            Message<?> message = MessageBuilder
                .withPayload(i)
                .setHeader("X-ROUTER", "to-string")
                .setHeader("X-COUNTER", i)
                .build();
            log.info("[###] Sending out the message: {}", message);
            this.printGateway.print(message);
        }

        for (int i = 0; i < 10; i++) {
            Message<?> message = MessageBuilder
                .withPayload(i)
                .setHeader("X-ROUTER", "to-int")
                .setHeader("X-COUNTER", i)
                .build();
            log.info("[###] Sending out the message to numeric: {}", message);
            this.printGateway.print(message);
        }

    }
}
