package com.langchain4j.learning.assistant;

public class RuvyAssistant {

    public static void main(String[] args) {
        VertexAICustomLLM vertexAICustomLLM = new VertexAICustomLLM();
        System.out.println("Response is" + vertexAICustomLLM.generate("What is the budget for this year?"));
    }

}
