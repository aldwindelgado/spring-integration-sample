package com.gitlab.aldwindelgado.springintegrationsample;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import com.gitlab.aldwindelgado.springintegrationsample.gateway.PrintGateway;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
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
    private final ObjectMapper mapper;

    public Application(PrintGateway printGateway,
        ObjectMapper mapper) {
        this.printGateway = printGateway;
        this.mapper = mapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String[] names = {
            "Liliana Shirey",
            "Christopher Burke",
            "Eliseo Laughlin",
            "Kanisha Doby",
            "Octavia Witzel",
            "Verna Deckman",
            "Xavier Orear",
            "Jenette Kelliher",
            "Shayne Eberhard",
            "Lelia Don",
            "Janice Hudman",
            "Brigida Tarnowski"
        };

        doSomething(this.populateDTO(names));
    }

    private List<Map<Object, Object>> populateDTO(String[] names) {

        Map<Object, Object> shitMap;
        List<Map<Object, Object>> theMap = new ArrayList<>();

        int counter = names.length - 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                shitMap = new HashMap<>();
                shitMap.put("firstName", names[counter].split(" ")[0]);
                shitMap.put("lastName", names[counter].split(" ")[1]);
                shitMap.put("version", i);
                theMap.add(shitMap); // add to list
                counter--;
            }
        }

        return theMap;
    }

    private void doSomething(List<Map<Object, Object>> dtos) {
        List<Future<Message<SampleDTO>>> futures = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            log.info("[###] DTO's MAP: {}", dtos.get(i));
            Message<Map<Object, Object>> message = MessageBuilder
                .withPayload(dtos.get(i))
                .build();
            futures.add(this.printGateway.printDTOString(message));
        }

        futures.forEach(messageFuture -> {
            try {
                log.info("[###] Future shit: {}", messageFuture.get().getPayload());
            } catch (InterruptedException | ExecutionException e) {
                log.info("[EXCEPTION] Error: {}", e.getMessage());
            }
        });
    }

}
