package com.mystiquex.ruvy.sql_transformer.util;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O;
import static dev.langchain4j.model.openai.OpenAiChatModelName.GPT_4_O_MINI;

public class AuthorService {

     static final String OPEN_AI_KEY = "{{OPEN_AI_KEY}}";
    public static void main(String[] args) {

        ChatLanguageModel chatLanguageModel = OpenAiChatModel.builder()
                .apiKey(OPEN_AI_KEY)
                .temperature(0.3)
                .modelName(GPT_4_O_MINI)
                .build();

        AuthorAssistant assistant = AiServices.create(AuthorAssistant.class,chatLanguageModel);
        String answer = assistant.getAuthorBio("Stephen Hawking");
        System.out.println(answer);

    }

}
