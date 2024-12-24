package com.mystiquex.ruvy.sql_transformer.config;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import dev.langchain4j.memory.ChatMemory;

import java.util.Arrays;
import java.util.List;

@Configuration
public class LangChainConfig {
    @Value("${ruvy.llm.projectID}")
    private String projectID;
    @Value("${ruvy.llm.location:us-east4}")
    private String location;
    @Value("${ruvy.llm.modelName}")
    private String modelName;

//    @Bean
//    public ChatLanguageModel chatModel() {
//
////        VertexAI vertexAi = new VertexAI("", "us-east4");
////
////        GenerationConfig generationConfig =
////                GenerationConfig.newBuilder()
////                        .setMaxOutputTokens(8192)
////                        .setTemperature(1F)
////                        .setTopP(0.95F)
////                        .build();
////        List<SafetySetting> safetySettings = Arrays.asList(
////                SafetySetting.newBuilder()
////                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
////                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
////                        .build(),
////                SafetySetting.newBuilder()
////                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
////                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
////                        .build(),
////                SafetySetting.newBuilder()
////                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
////                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
////                        .build(),
////                SafetySetting.newBuilder()
////                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
////                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
////                        .build()
////        );
////        GenerativeModel model =
////                new GenerativeModel.Builder()
////                        .setModelName("")
////                        .setVertexAi(vertexAi)
////                        //.setGenerationConfig(generationConfig)
////                        .setSafetySettings(safetySettings)
////                        .build();
//
////        return new VertexAiGeminiChatModel(model,generationConfig,1);
//////        // Configure the OpenAI Chat Model or your custom LLM
////        VertexAI
////
////        return VertexAiGeminiChatModel.builder()
////                .project(projectID) // Replace with your LLM's API key
////                .location(location) // Adjust the model's creativity
////                .modelName(modelName)
////                .
////                .build();
//    }

//    @Bean
//    public ChatMemory chatMemory(ChatLanguageModel chatLanguageModel) {
//        ChatMemoryStore memoryStore = new InMemoryChatMemoryStore(); // Optionally, replace with a persistent store
//        return ChatMemory.builder()
//                .chatModel(chatModel)
//                .memoryStore(memoryStore)
//                .build();
//    }

}
