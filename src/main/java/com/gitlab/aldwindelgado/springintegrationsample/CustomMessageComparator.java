package com.gitlab.aldwindelgado.springintegrationsample;

import java.util.Comparator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;

/**
 * @author Aldwin Delgado
 */
@Slf4j
public class CustomMessageComparator implements Comparator<Message<?>> {

    @Override
    public int compare(Message<?> message, Message<?> message2) {
        String payload = message.getPayload().toString();
        String payload2 = message2.getPayload().toString();

        boolean isPayloadEven = Integer.valueOf(payload.substring(payload.length() - 1)) % 2 == 0;
        boolean isPayload2Even =
            Integer.valueOf(payload2.substring(payload2.length() - 1)) % 2 == 0;

        if ((isPayloadEven && isPayload2Even) || (!isPayloadEven && !isPayload2Even)) {
            return 0;
        } else if (isPayloadEven) {
            return -1;
        } else {
            return 1;
        }
    }
}
