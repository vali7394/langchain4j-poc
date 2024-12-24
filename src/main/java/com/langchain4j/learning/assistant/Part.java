package com.langchain4j.learning.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Part {

    @JsonProperty("text")
    private String text;

    // Getters and Setters

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
