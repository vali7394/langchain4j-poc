package com.langchain4j.learning.assistant;

import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vertexai.api.GenerationConfig;
import com.google.cloud.vertexai.api.HarmCategory;
import com.google.cloud.vertexai.api.SafetySetting;
import com.google.cloud.vertexai.generativeai.GenerativeModel;
import com.langchain4j.learning.model.Musician;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.TokenCountEstimator;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiChatModelName;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.model.vertexai.VertexAiGeminiChatModel;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static dev.langchain4j.model.openai.OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002;

public class MusicianAssistant {

    public static final String OPEN_AI_KEY = "";

    public static void main(String[] args) {
        ChatLanguageModel chatLanguageModel = OpenAiChatModel.builder()
                .apiKey(OPEN_AI_KEY)
                .modelName(OpenAiChatModelName.GPT_4_O_MINI)
                .temperature(0.3)
                .timeout(Duration.ofMillis(10000))
                .logRequests(true)
                .logResponses(true)
                .build();

       // TokenCountEstimator countEstimator = OpenAiChatModel.builder().build().tokenCountEstimator();

                ChatLanguageModel chatLanguageModel1 = VertexAiGeminiChatModel.builder()
                .project("385795636526")
                .location("us-east4")
                .modelName("8748660640341557248")
                .maxRetries(1)
                //.timeout(Duration.ofMillis(10000))
              //  .logRequests(true)
               // .logResponses(true)
                .build();
        VertexAI vertexAi = new VertexAI("385795636526", "us-east4");



//        GenerationConfig generationConfig =
//                GenerationConfig.newBuilder()
//                        .setMaxOutputTokens(8192)
//                        .setTemperature(1F)
//                        .setTopP(0.95F)
//                        .build();
//        List<SafetySetting> safetySettings = Arrays.asList(
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_HATE_SPEECH)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_DANGEROUS_CONTENT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_SEXUALLY_EXPLICIT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
//                        .build(),
//                SafetySetting.newBuilder()
//                        .setCategory(HarmCategory.HARM_CATEGORY_HARASSMENT)
//                        .setThreshold(SafetySetting.HarmBlockThreshold.BLOCK_ONLY_HIGH)
//                        .build());
//
//        GenerativeModel model =
//                new GenerativeModel.Builder()
//                        .setModelName("projects/385795636526/locations/us-east4/endpoints/2267936246334488576")
//                        .setVertexAi(vertexAi)
//                        .setGenerationConfig(generationConfig)
//                        .setSafetySettings(safetySettings)
//                        .build();
//        ChatLanguageModel  vertexAiGeminiChatModel = new VertexAiGeminiChatModel(model,generationConfig,1);

        MusicianAssistant assistant = new MusicianAssistant();
        tokenCountInvoker("Miles Davis");
       // System.out.println(assistant.generateTopThreeAlbums("Miles Davis",chatLanguageModel));
    }



     public Musician generateTopThreeAlbums(String musicianName, ChatLanguageModel chatLanguageModel){
        SystemMessage systemMessage = SystemMessage.from("""
                You are expert in JAZZ music.
                Reply with only the names of the artists , albums , etc.
                Be very Concise.
                If a list is given , seperate the items with Comma.
                """
        );

        UserMessage userMessage = UserMessage.from(
                String.format("Only list the top three albums of %s", musicianName));

        List<ChatMessage> messageList = List.of(systemMessage,userMessage);

        Response<AiMessage> response = chatLanguageModel.generate(messageList);

        String topThreeAlbums = response.content().text();

        return new Musician(musicianName,topThreeAlbums);

    }


    private static void tokenCountInvoker(String musicianName) {
        SystemMessage systemMessage = SystemMessage.from("""
                You are expert in JAZZ music.
                Reply with only the names of the artists , albums , etc.
                Be very Concise.
                If a list is given , seperate the items with Comma.
                """
        );

        tokenizationExample(systemMessage);

        UserMessage userMessage = UserMessage.from(
                String.format("Only list the top three albums of %s", musicianName));

        tokenizationExample(userMessage);

    }

    private static void tokenizationExample(ChatMessage chatMessage) {

        OpenAiTokenizer openAiTokenizer = new OpenAiTokenizer(TEXT_EMBEDDING_ADA_002);
        System.out.println("Token Count" + openAiTokenizer.estimateTokenCountInMessage(chatMessage));
        System.out.println(chatMessage.toString());
       List<Integer> tokens = openAiTokenizer.encode(chatMessage.toString());
        tokens.forEach(System.out::println);
        System.out.println(openAiTokenizer.decode(tokens));
    }

}
