package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.gateway.PrintGateway;
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

        String[] payloads = {"A B", "C D", "E F"};
        for (int i = 0; i < payloads.length; i++) {
            Message<?> message = MessageBuilder
                .withPayload(payloads[i])
                .build();
            log.info("[###] Sending out the message: {}", message);
            this.printGateway.print(message);
        }

    }
}
