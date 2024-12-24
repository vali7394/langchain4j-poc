package com.langchain4j.learning.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerationConfig {

    @JsonProperty("temperature")
    private double temperature;

    @JsonProperty("maxOutputTokens")
    private int maxOutputTokens;

    // Getters and Setters

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public int getMaxOutputTokens() {
        return maxOutputTokens;
    }

    public void setMaxOutputTokens(int maxOutputTokens) {
        this.maxOutputTokens = maxOutputTokens;
    }
}