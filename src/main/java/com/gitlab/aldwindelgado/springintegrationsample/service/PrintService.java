package com.gitlab.aldwindelgado.springintegrationsample.service;

import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Service
public class PrintService {


    public void print(Message<String> message) {
        log.info("[###] Message from print service: {}", message.getPayload());

        MessageHeaders headers = message.getHeaders();

        for (Entry<String, Object> entry : headers.entrySet()) {
            log.info("[###] Key: {}", entry.getKey());
            log.info("[###] Value: {}", entry.getValue());
        }
    }

}
