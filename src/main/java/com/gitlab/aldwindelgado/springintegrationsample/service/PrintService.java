package com.gitlab.aldwindelgado.springintegrationsample.service;

import com.gitlab.aldwindelgado.springintegrationsample.domain.SampleDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@MessageEndpoint
public class PrintService {

    @ServiceActivator(inputChannel = "outputChannel")
    public SampleDTO printDto(SampleDTO sampleDTO) {
        SampleDTO sampleDTO1 = sampleDTO;
        sampleDTO1.setFullName(sampleDTO.getFullName().toUpperCase());

        return sampleDTO1;
    }

    @ServiceActivator(inputChannel = "inputChannel", outputChannel = "outputChannel")
    public SampleDTO evaluateSampleDto(SampleDTO sampleDTO) {

        SampleDTO sampleDTO1 = new SampleDTO();
        sampleDTO1.setVersion(sampleDTO.getVersion());
        sampleDTO1.setFullName(String.format("%s, %s",
            sampleDTO.getLastName(), sampleDTO.getFirstName()));

        return sampleDTO1;
    }

}
