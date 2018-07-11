package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.service.PrintService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

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
        List<Future<Message<String>>> futures = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Message<String> message = MessageBuilder
                .withPayload(
                    String.format("PAYLOAD %d", i))
                .setHeader("X-MESSAGE-NUMBER", 500)
                .setHeader("X-COUNTER", i)
                .build();
            log.info("[###] Sending out the message: {}", message);
            futures.add(this.printGateway.print(message));
        }

        for (Future<Message<String>> future : futures) {
            log.info("[###] From future loop: {}", future.get().getPayload());
        }
    }
}
