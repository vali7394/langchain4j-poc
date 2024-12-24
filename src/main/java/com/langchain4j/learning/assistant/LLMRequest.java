package com.langchain4j.learning.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LLMRequest {

    @JsonProperty("contents")
    private List<Content> contents;

    @JsonProperty("systemInstruction")
    private SystemInstruction systemInstruction;

    @JsonProperty("generationConfig")
    private GenerationConfig generationConfig;

    @JsonProperty("safetySettings")
    private List<SafetySetting> safetySettings;

    // Getters and Setters

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public SystemInstruction getSystemInstruction() {
        return systemInstruction;
    }

    public void setSystemInstruction(SystemInstruction systemInstruction) {
        this.systemInstruction = systemInstruction;
    }

    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }

    public List<SafetySetting> getSafetySettings() {
        return safetySettings;
    }

    public void setSafetySettings(List<SafetySetting> safetySettings) {
        this.safetySettings = safetySettings;
    }

}
