package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import com.gitlab.aldwindelgado.springintegrationsample.gateway.PrintGateway;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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

        Map<Object, Object> shitMap;
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
        List<Future<Message<SampleDTO>>> futures = new ArrayList<>();
        for (int i = 0; i < dtos.size(); i++) {
            log.info("[###] DTO's MAP: {}", dtos.get(i));
//            Message<SampleDTO> message = MessageBuilder
//                .withPayload(dtos.get(i))
//                .setHeader("replyChannel", "outputChannel")
//                .build();
            futures.add(this.printGateway.print(dtos.get(i)));
        }

        futures.forEach(messageFuture -> {
            try {
                log.info("[###] Future shit PAYLOAD: {}", messageFuture.get().getPayload());
                log.info("[###] Future shit HEADERS: {}", messageFuture.get().getHeaders());
            } catch (InterruptedException | ExecutionException e) {
                log.info("[EXCEPTION] Error: {}", e.getMessage());
            }
        });
    }

}
