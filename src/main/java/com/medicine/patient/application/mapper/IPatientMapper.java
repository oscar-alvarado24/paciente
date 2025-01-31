package com.medicine.patient.application.mapper;

import com.medicine.patient.application.dto.PatientRequest;
import com.medicine.patient.application.dto.RequestResponse;
import com.medicine.patient.domain.model.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface IPatientMapper {
     Patient toPatient (PatientRequest patientRequest);

    RequestResponse toRequestResponse (Patient patient);
}
