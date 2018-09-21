package com.github.aldwindelgado.springintegrationsample.gateway;

import com.github.aldwindelgado.springintegrationsample.domain.SampleDTO;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.util.concurrent.ListenableFuture;

@MessagingGateway
public interface PrintGateway {

    @Gateway(
        requestChannel = "inputChannel",
        headers = {
            @GatewayHeader(name = "methodHeader", value = "method-header-value"),
            @GatewayHeader(name = "fullName", expression = "#args[0].lastName.toUpperCase() + ', ' + #args[0].firstName.toUpperCase()")
        }
    )
    ListenableFuture<Message<SampleDTO>> print(SampleDTO sampleDTO);
}
