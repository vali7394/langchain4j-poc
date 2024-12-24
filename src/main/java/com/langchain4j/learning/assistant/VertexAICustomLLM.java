package com.langchain4j.learning.assistant;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import okhttp3.*;

import java.io.IOException;

public class VertexAICustomLLM {


    private static final String SCOPE = "https://www.googleapis.com/auth/cloud-platform";
    private final OkHttpClient client;
    ObjectMapper objectMapper;

    private final String API_ENDPOINT = "https://us-east4-aiplatform.googleapis.com/v1/projects/{{project-id}}/locations/{{location-name}}/endpoints/{{model-id}}:generateContent";

    public VertexAICustomLLM() {
        this.client = new OkHttpClient();
        objectMapper = new ObjectMapper();
    }


    public String generate(String userMessage) {
        try {
            // Generate Access Token
            String accessToken = getAccessToken();

            // Create Request Payload
            String requestPayload = createRequestPayload(userMessage);

            // Call Vertex AI API
            return callVertexAiApi(accessToken, requestPayload);

            // Parse Response (adjust parsing based on Vertex AI API response format)
          //  return parseResponse(response);

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate response from Vertex AI", e);
        }
    }

    private String getAccessToken() throws IOException {
        GoogleCredentials credentials = GoogleCredentials.getApplicationDefault().createScoped(SCOPE);
        credentials.refreshIfExpired();
        AccessToken token = credentials.getAccessToken();
        return token.getTokenValue();
    }

    private String createRequestPayload(String userMessage) {

        String systemMessage = "You are a SQL query generator for the PostgreSQL Database.Don't prefix with sql Schema:\n" +
                "Table: budget\n" +
                "   Columns: budget_id (integer), domain_id (integer), total_budget (numeric), remaining_budget (numeric), " +
                "spent (numeric), projected (numeric), variance (numeric), planning_year (integer), budget_january (numeric), " +
                "budget_february (numeric), budget_march (numeric), budget_april (numeric), budget_may (numeric), " +
                "budget_june (numeric), budget_july (numeric), budget_august (numeric), budget_september (numeric), " +
                "budget_october (numeric), budget_november (numeric), budget_december (numeric), created_timestamp (timestamp), " +
                "last_modified_timestamp (timestamp)\n" +
                "Table: domain\n" +
                "   Columns: domain_id (integer), domain_name (varchar), dept_id (integer), director_id (integer), " +
                "created_timestamp (timestamp), last_modified_timestamp (timestamp), initial_open (integer), current_open (integer)";

        String requestPayload = "{\n" +
                "    \"contents\": [\n" +
                "        {\n" +
                "            \"role\": \"user\",\n" +
                "            \"parts\": [\n" +
                "                {\n" +
                "                     \"text\": \"" + userMessage.replace("\"", "\\\"") + "\"\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ],\n" +
                "    \"systemInstruction\": {\n" +
                "        \"role\": \"System\",\n" +
                "        \"parts\": [\n" +
                "            {\n" +
                "                \"text\": \"" + systemMessage.replace("\n", "\\n") + "\"\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"generationConfig\": {\n" +
                "        \"temperature\": 0.7,\n" +
                "        \"maxOutputTokens\": 256\n" +
                "    },\n" +
                "    \"safetySettings\": [\n" +
                "        {\n" +
                "            \"category\": \"HARM_CATEGORY_HATE_SPEECH\",\n" +
                "            \"threshold\": \"OFF\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"category\": \"HARM_CATEGORY_DANGEROUS_CONTENT\",\n" +
                "            \"threshold\": \"OFF\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"category\": \"HARM_CATEGORY_SEXUALLY_EXPLICIT\",\n" +
                "            \"threshold\": \"OFF\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"category\": \"HARM_CATEGORY_HARASSMENT\",\n" +
                "            \"threshold\": \"OFF\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";



        return requestPayload;
    }

    private String callVertexAiApi(String accessToken, String requestPayload) throws IOException {
        Request request = new Request.Builder()
                .url(API_ENDPOINT)
                .post(RequestBody.create(requestPayload, MediaType.get("application/json")))
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected HTTP response: " + response.code() + ", " + response.message());
            }

            LLMResponse apiResponse = objectMapper.readValue(response.body().string(), LLMResponse.class);

            // Access the parsed data
            LLMResponse.AssistantResponse candidate = apiResponse.getCandidates().get(0);
            String rawSql = candidate.getContent().getParts().get(0).getText();


            // Clean the SQL query (remove unwanted characters like markdown syntax)
            String formattedSql = rawSql.replaceAll("```sql\\n", "")
                    .replaceAll("\\n```", "")
                    .replaceAll("\\n", " ")
                    .trim();

            return formattedSql;
        }
    }

    private String parseResponse(String response) {
        // Parse the JSON response and extract the content
        // Use a JSON library like Gson or Jackson for structured parsing
        return extractPlainSql(response); // Simplified for demonstration
    }

    private static String extractPlainSql(String apiResponse) {
        // Remove markdown syntax like ```sql and ```
        String sql = apiResponse.replaceAll("```sql\\n", "")
                .replaceAll("\\n```", "")
                .replaceAll("\\n", " ")
                .replaceAll("\\s{2,}", " ")
                .trim();
        // Extract only the "text" field's value
        if (sql.contains("\"text\":")) {
            sql = sql.substring(sql.indexOf("\"text\":") + 8);
            sql = sql.replaceAll("^\"|\"$", ""); // Remove wrapping quotes
        }
        return sql.trim();
    }


}
