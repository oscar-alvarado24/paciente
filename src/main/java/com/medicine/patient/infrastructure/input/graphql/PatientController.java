package com.medicine.patient.infrastructure.input.graphql;

import com.medicine.patient.application.dto.PatientRequest;
import com.medicine.patient.application.dto.RequestResponse;
import com.medicine.patient.application.handler.IPatientHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.Arguments;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

@Component
@RequiredArgsConstructor
@RestController
public class PatientController {

    private static final Logger log = LoggerFactory.getLogger(PatientController.class);
    private final IPatientHandler patientHandler;
    @MutationMapping
    public String createPatient (@Arguments PatientRequest patient) {
        return patientHandler.createPatient(patient);
    }

    @QueryMapping
    public RequestResponse getPatient (@Argument String email){
        return patientHandler.getPatient(email);
    }

    @QueryMapping
    public String validateStatus (@Argument String email){
        return patientHandler.validateStatus(email);
    }

    @MutationMapping
    public String changeStatus (@Argument int id, @Argument String status){
        return patientHandler.changeStatus(id, status);
    }
}
