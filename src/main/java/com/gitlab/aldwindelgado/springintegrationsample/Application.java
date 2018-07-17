package com.gitlab.aldwindelgado.springintegrationsample;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import com.gitlab.aldwindelgado.springintegrationsample.gateway.PrintGateway;
import java.util.ArrayList;
import java.util.List;
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

        List<SampleDTO> dtos = new ArrayList<>();

        SampleDTO dto = new SampleDTO();
        int counter = names.length - 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                dto.setFirstName(names[counter].split(" ")[0]);
                dto.setLastName(names[counter].split(" ")[1]);
                dto.setVersion(i);
                dtos.add(dto); // add to list

                dto = new SampleDTO();
                counter--;
            }
        }

        return dtos;
    }

    private void doSomething(List<SampleDTO> dtos) {
        List<Future<Message<SampleDTO>>> futures = new ArrayList<>();

        for (int i = 0; i < dtos.size(); i++) {
            log.info("[###] DTO: {}", dtos.get(i));
            Message<SampleDTO> message = MessageBuilder
                .withPayload(dtos.get(i))
                .build();
            futures.add(this.printGateway.printDTO(message));
        }
    }

}
