package com.github.aldwindelgado.springintegrationsample;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.aldwindelgado.springintegrationsample.domain.SampleDTO;
import com.github.aldwindelgado.springintegrationsample.gateway.PrintGateway;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

    private final PrintGateway printGateway;
    private final ObjectMapper objectMapper;

    public Application(PrintGateway printGateway,
        ObjectMapper objectMapper) {
        this.printGateway = printGateway;
        this.objectMapper = objectMapper;
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

    private List<SampleDTO> populateDTO(String[] names) {

        List<SampleDTO> theMap = new ArrayList<>();
        SampleDTO sampleDTO;

        int counter = names.length - 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                sampleDTO = new SampleDTO();
                sampleDTO.setFirstName(names[counter].split(" ")[0]);
                sampleDTO.setLastName(names[counter].split(" ")[1]);
                sampleDTO.setVersion(i);
                theMap.add(sampleDTO); // add to list
                counter--;
            }
        }

        return theMap;
    }

    private void doSomething(List<SampleDTO> dtos) {
        for (int i = 0; i < dtos.size(); i++) {
            log.info("[###] DTO's MAP: {}", dtos.get(i));
            ListenableFuture<Message<SampleDTO>> theFuture = this.printGateway
                .print(dtos.get(i));
            theFuture.addCallback(new ListenableFutureCallback<Message<?>>() {
                @Override
                public void onFailure(Throwable ex) {
                    log.info("[###] FAILURE MESSAGE: {}", ex.getMessage());
                    ex.printStackTrace();
                }

                @Override
                public void onSuccess(Message<?> result) {
                    log.info("[###] SUCCESS PAYLOAD: {}",
                        prettifyToJsonString(result.getPayload()));
                    log.info("[###] SUCCESS HEADERS: {}",
                        prettifyToJsonString(result.getHeaders()));
                }
            });
        }
    }

    private String prettifyToJsonString(Object object) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException jpe) {
            log.error("[###] JPE ORIG MSG: {}", jpe.getOriginalMessage());
            log.error("[###] JPE MSG: {}", jpe.getMessage());
        }

        return null;
    }

}
