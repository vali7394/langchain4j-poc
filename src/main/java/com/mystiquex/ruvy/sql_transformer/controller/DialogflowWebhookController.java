package com.mystiquex.ruvy.sql_transformer.controller;



import com.mystiquex.ruvy.sql_transformer.models.DialogflowRequest;
import com.mystiquex.ruvy.sql_transformer.models.DialogflowResponse;
import com.mystiquex.ruvy.sql_transformer.models.SystemInstructions;
import com.mystiquex.ruvy.sql_transformer.service.VertexAiService;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/webhook")

public class DialogflowWebhookController {

    private final VertexAiService vertexAiService;

    public DialogflowWebhookController(VertexAiService vertexAiService) {
        this.vertexAiService = vertexAiService;
    }

//    ChatLanguageModel chatLanguageModel;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public DialogflowResponse handleDialogflowRequest(@RequestBody DialogflowRequest request) {
        // Extract Intent and Parameters
        String intent = request.getQueryResult().getIntent().getDisplayName();
        String userQuery = request.getQueryResult().getQueryText();

        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        // Custom logic based on the intent
       // String responseText;
//        switch (intent) {
//            case "CheckApplicationStatus":
//                responseText = "Your application is currently under review. You will be notified soon.";
//                break;
//            case "HolidaySchedule":
//                responseText = "Our office will be closed from Dec 24 to Jan 2 for the holidays.";
//                break;
//            default:
//                responseText = "I'm sorry, I don't have an answer for that.";
//                break;
//        }
        String responseText;
        try {
             responseText = vertexAiService.predict(
                    "What was the budget for this year compared to last year?");

        } catch (Exception exception) {
          exception.printStackTrace();
           return new DialogflowResponse(exception.getMessage());
        }
        // Store the response in chat memory

        return new DialogflowResponse(responseText);

    }
}
