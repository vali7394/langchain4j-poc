package com.langchain4j.learning.assistant;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;

import static com.langchain4j.learning.assistant.MusicianAssistant.OPEN_AI_KEY;

public class ChatMemoryExample {

    public static void main(String[] args) {
        ChatLanguageModel chatLanguageModel  = OpenAiChatModel.builder()
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .apiKey(OPEN_AI_KEY)
                .logRequests(true)
                .logResponses(true)
                .build();

        ChatMemory chatMemory = MessageWindowChatMemory
                .builder().maxMessages(20).build();

        chatMemory.add(new UserMessage("My name is "));
        AiMessage aiMessageFirst = chatLanguageModel.generate(chatMemory.messages()).content();
        chatMemory.add(aiMessageFirst);
        System.out.println(aiMessageFirst.text());

        chatMemory.add(new UserMessage("My Favourite Cricket team is "));
        AiMessage aiMessageSecond = chatLanguageModel.generate(chatMemory.messages()).content();
        chatMemory.add(aiMessageSecond);
        System.out.println(aiMessageSecond.text());


        chatMemory.add(new UserMessage("When was their last match player"));
        AiMessage aiMessageThird = chatLanguageModel.generate(chatMemory.messages()).content();
        chatMemory.add(aiMessageThird);
        System.out.println(aiMessageThird.text());

        chatMemory.add(new UserMessage("WHats the name of the captain"));
        AiMessage aiMessageFourth = chatLanguageModel.generate(chatMemory.messages()).content();
        chatMemory.add(aiMessageFourth);
        System.out.println(aiMessageFourth.text());

        chatMemory.add(new UserMessage("What's My Name"));
        AiMessage aiMessageFifth = chatLanguageModel.generate(chatMemory.messages()).content();
        chatMemory.add(aiMessageFifth);
        System.out.println(aiMessageFifth.text());


    }

}
