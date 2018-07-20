package com.gitlab.aldwindelgado.springintegrationsample.gateway;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import java.util.List;
import java.util.concurrent.Future;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

@MessagingGateway(
    defaultRequestChannel = "inputChannel",
    defaultHeaders = {
        @GatewayHeader(name = "messagingGatewayHeader", value = "theHeaderValue"),
        @GatewayHeader(name = "anotherShittyHeader", value = "theOtherShittyValue")
    }
)
public interface PrintGateway {

    @Gateway(
        requestChannel = "inputChannel",
        headers = {
            @GatewayHeader(name = "method-header", value = "method-header-value"),
            @GatewayHeader(name = "first-name", expression = "#args[0].firstName")
        }
    )
    Future<Message<SampleDTO>> print(SampleDTO sampleDTO);

    @Gateway(requestChannel = "aggregatorChannel")
    ListenableFuture<Message<SampleDTO>> printWithoutHeader(SampleDTO sampleDTO);

    @Gateway(requestChannel = "aggregatorChannel")
    ListenableFuture<Message<SampleDTO>> printWithoutHeader(List<SampleDTO> sampleDTO);

    @Gateway(requestChannel = "httpOutboundChannel")
    ListenableFuture<Message<SampleDTO>> printWithoutHeader(String sampleDTO);
}
