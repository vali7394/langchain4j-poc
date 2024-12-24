package com.langchain4j.learning.assistant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LLMResponse {

    @JsonProperty("candidates")
    private List<AssistantResponse> assistantResponse;

    @JsonProperty("usageMetadata")
    private UsageMetadata usageMetadata;

    // Getters and Setters

    public List<AssistantResponse> getCandidates() {
        return assistantResponse;
    }

    public void setCandidates(List<AssistantResponse> candidates) {
        this.assistantResponse = candidates;
    }

    public UsageMetadata getUsageMetadata() {
        return usageMetadata;
    }

    public void setUsageMetadata(UsageMetadata usageMetadata) {
        this.usageMetadata = usageMetadata;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AssistantResponse {

        @JsonProperty("content")
        private Content content;

        @JsonProperty("finishReason")
        private String finishReason;

        @JsonProperty("avgLogprobs")
        private Double avgLogprobs;

        // Getters and Setters

        public Content getContent() {
            return content;
        }

        public String getFinishReason() {
            return finishReason;
        }

        public void setFinishReason(String finishReason) {
            this.finishReason = finishReason;
        }

        public Double getAvgLogprobs() {
            return avgLogprobs;
        }

        public void setAvgLogprobs(Double avgLogprobs) {
            this.avgLogprobs = avgLogprobs;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Content {

        @JsonProperty("role")
        private String role;

        @JsonProperty("parts")
        private List<Part> parts;

        // Getters and Setters

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UsageMetadata {

        @JsonProperty("promptTokenCount")
        private int promptTokenCount;

        @JsonProperty("candidatesTokenCount")
        private int candidatesTokenCount;

        @JsonProperty("totalTokenCount")
        private int totalTokenCount;

        // Getters and Setters

        public int getPromptTokenCount() {
            return promptTokenCount;
        }

        public void setPromptTokenCount(int promptTokenCount) {
            this.promptTokenCount = promptTokenCount;
        }

        public int getCandidatesTokenCount() {
            return candidatesTokenCount;
        }

        public void setCandidatesTokenCount(int candidatesTokenCount) {
            this.candidatesTokenCount = candidatesTokenCount;
        }

        public int getTotalTokenCount() {
            return totalTokenCount;
        }

        public void setTotalTokenCount(int totalTokenCount) {
            this.totalTokenCount = totalTokenCount;
        }
    }

}
