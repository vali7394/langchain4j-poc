package com.langchain4j.learning.assistant.extending;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.agent.tool.ToolSpecifications;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.service.tool.DefaultToolExecutor;
import dev.langchain4j.service.tool.ToolExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.langchain4j.learning.assistant.MusicianAssistant.OPEN_AI_KEY;

public class ToolsExample {


    public static void main(String[] args) {
        LegalDocumentsTools legalDocumentsTools = new LegalDocumentsTools();
        List<ToolSpecification> specificationList = ToolSpecifications
                .toolSpecificationsFrom(legalDocumentsTools.getClass());
        ChatLanguageModel chatLanguageModel  = OpenAiChatModel.builder()
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .apiKey(OPEN_AI_KEY)
                .logRequests(true)
                .logResponses(true)
                .build();

        // User query
        List<ChatMessage> chatMessages = new ArrayList<>();
        UserMessage userMessage = UserMessage.from("When was the PRIVACY document updated?");
        chatMessages.add(userMessage);

        AiMessage aiMessage = chatLanguageModel.generate(chatMessages,specificationList).content();
        List<ToolExecutionRequest> toolExecutionRequests = aiMessage.toolExecutionRequests();
        chatMessages.add(aiMessage);
        toolExecutionRequests
                .forEach(toolExecutionRequest -> {
                    ToolExecutor toolExecutor = new DefaultToolExecutor(legalDocumentsTools,toolExecutionRequest);
                    String result = toolExecutor.execute(toolExecutionRequest, UUID.randomUUID());
                    ToolExecutionResultMessage executionResultMessage = ToolExecutionResultMessage.from(toolExecutionRequest,result);
                    chatMessages.add(executionResultMessage);
                });
        AiMessage finalResponse = chatLanguageModel.generate(chatMessages).content();
        System.out.println(finalResponse.text());
    }

}
