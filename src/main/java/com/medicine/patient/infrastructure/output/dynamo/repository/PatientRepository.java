package com.medicine.patient.infrastructure.output.dynamo.repository;

import com.medicine.patient.infrastructure.output.dynamo.entity.PatientEntity;
import com.medicine.patient.infrastructure.util.Constants;
import software.amazon.awssdk.core.pagination.sync.SdkIterable;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import java.util.Optional;

public class PatientRepository implements IPatientRepository {
    /**
     * @param email of patient to search
     * @param table object to operate the table dynamo
     * @return optional on patient
     */
    @Override
    public Optional<PatientEntity> findPatientByEmail(String email, DynamoDbTable<PatientEntity> table) {
        DynamoDbIndex<PatientEntity> dbIndex = table.index(Constants.EMAIL_INDEX);

        // Perform the query for the attribute
        QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(email)
                .build());

        SdkIterable<Page<PatientEntity>> results = dbIndex.query(builder -> builder
                .queryConditional(queryConditional));

        return results.stream()
                .flatMap(page -> page.items().stream())
                .findFirst();
    }

    /**
     * @param patient to save
     * @param table   object to operate the table dynamo
     * @return message of confirmation
     */
    @Override
    public String save(PatientEntity patient, DynamoDbTable<PatientEntity> table) {
        table.putItem(patient);
        return String.format(Constants.MSG_CREATE_PATIENT, patient.getFirstName(), patient.getFirstSurName());
    }

    /**
     * @param id of patient to search
     * @param table object to operate the table dynamo
     * @return optional on patient
     */
    @Override
    public Optional<PatientEntity> findPatientById(String id, DynamoDbTable<PatientEntity> table) {
        Key key = Key.builder().partitionValue(id).build();
        return Optional.ofNullable(table.getItem(key));
    }

    /**
     * @param patient
     * @param table
     * @return
     */
    @Override
    public String updatePatient(PatientEntity patient, DynamoDbTable<PatientEntity> table) {
        table.updateItem(patient);
        return Constants.MSG_UPDATE_PATIENT;
    }
}

