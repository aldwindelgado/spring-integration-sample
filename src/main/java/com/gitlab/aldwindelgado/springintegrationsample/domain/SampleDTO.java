package com.gitlab.aldwindelgado.springintegrationsample.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aldwin Delgado
 */
@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SampleDTO implements Serializable {

    private String firstName;
    private String lastName;
    private String fullName;
    private Integer version;

}
