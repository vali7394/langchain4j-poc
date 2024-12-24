package com.langchain4j.learning.assistant;

import dev.ai4j.openai4j.chat.UserMessage;
import dev.langchain4j.data.image.Image;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.PromptTemplate;
import dev.langchain4j.model.language.LanguageModel;
import dev.langchain4j.model.openai.*;
import dev.langchain4j.model.output.Response;

import java.util.HashMap;
import java.util.Map;

import static com.langchain4j.learning.assistant.MusicianAssistant.OPEN_AI_KEY;

public class PromptTemplateExample {

    public static void main(String[] args) {
       ChatLanguageModel chatLanguageModel  = OpenAiChatModel.builder()
               .modelName(OpenAiChatModelName.GPT_4_O_MINI)
               .apiKey(OPEN_AI_KEY)
               .logRequests(true)
               .logResponses(true)
               .build();

      System.out.println(chatLanguageModel.generate("My name is "));
      System.out.println(chatLanguageModel.generate("My Favourite Cricket team is "));
      System.out.println(chatLanguageModel.generate("When was their last match player"));
      System.out.println(chatLanguageModel.generate("WHats the name of the captain"));
      System.out.println(chatLanguageModel.generate("What's My Name"));

    }


    private static void imageModelExample(){

        OpenAiImageModel openAiImageModel = OpenAiImageModel.builder()
                .modelName(OpenAiImageModelName.DALL_E_2)
                .apiKey(OPEN_AI_KEY)
                .logRequests(true)
                .logResponses(true)
                .build();

        PromptTemplate promptTemplate = PromptTemplate.from("Generate an Image where couple dancing in {{item}} beside beautiful {{place}} with trees and boards scenery");

        Map<String,Object> promptMAp = new HashMap<>();
        promptMAp.put("item", "rain");
        promptMAp.put("place", "forest");

        Prompt prompt = promptTemplate.apply(promptMAp);


        // UserMessage userMessage = UserMessage.from();

        Response<Image> response= openAiImageModel.generate(prompt.text());
        System.out.println(response.content().url());

    }


}
