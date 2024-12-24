package com.langchain4j.learning.assistant;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Content {

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

