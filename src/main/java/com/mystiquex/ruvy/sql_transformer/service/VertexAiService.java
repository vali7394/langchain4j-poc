package com.mystiquex.ruvy.sql_transformer.service;

import com.google.cloud.vertexai.api.EndpointName;
import com.google.cloud.vertexai.api.PredictRequest;
import com.google.cloud.vertexai.api.PredictResponse;
import com.google.cloud.vertexai.api.PredictionServiceClient;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class VertexAiService {

    private static final String PROJECT_ID = "";
    private static final String LOCATION = "us-east4";
    private static final String MODEL_NAME = "";// Your custom endpoint ID
    private static final String SYSTEM_INSTRUCTIONS = "You are a SQL query generator for the PostgreSQL Database. Schema:\\nTable: budget\\n  Columns: budget_id (integer), domain_id (integer), total_budget (numeric), remaining_budget (numeric), spent (numeric), projected (numeric), variance (numeric), planning_year (integer) ,budget_january (numeric), budget_february (numeric),budget_march (numeric), budget_april (numeric),budget_may (numeric),budget_june (numeric), budget_july (numeric), budget_august (numeric), budget_september (numeric), budget_october (numeric), budget_november (numeric),budget_december (numeric), created_timestamp (timestamp), last_modified_timestamp (timestamp)\\nTable: domain\\n  Columns: domain_id (integer), domain_name (varchar), dept_id (integer), director_id (integer), created_timestamp (timestamp), last_modified_timestamp (timestamp), initial_open (integer), current_open (integer)";

    public String predict(String userMessage) throws IOException {
        // Initialize PredictionServiceClient
        try (PredictionServiceClient predictionServiceClient = PredictionServiceClient.create()) {

            // Build model endpoint
            String modelEndpoint = String.format(
                    "projects/%s/locations/%s/models/%s",
                    PROJECT_ID, LOCATION, MODEL_NAME
            );

            // Construct request payload
            Value.Builder instanceBuilder = Value.newBuilder();
            instanceBuilder.getStructValueBuilder()
                    .putFields("content", Value.newBuilder().setStringValue(userMessage).build());

            PredictRequest predictRequest = PredictRequest.newBuilder()
                    .setEndpoint(modelEndpoint)
                    .addAllInstances(List.of(instanceBuilder.build()))
                    .build();

            // Perform prediction
            PredictResponse predictResponse = predictionServiceClient.predict(predictRequest);

            // Extract response
            return JsonFormat.printer().print(predictResponse.getPredictions(0));
        }
    }
}
