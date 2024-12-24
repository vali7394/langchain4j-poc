package com.lanchain4j.learning;

import com.github.dockerjava.api.model.Image;
import com.langchain4j.learning.assistant.MusicianAssistant;
import com.langchain4j.learning.model.Musician;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.ollama.OllamaContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class MusicianAssistantTest {

    static String MODEL_NAME = "tinyllama";


    @Test
    public void shouldGenerateMusicianTopThreeAlbums() throws IOException, InterruptedException {
        ChatLanguageModel languageModel;
        try (OllamaContainer ollamaContainer = buildOllamaContainer()) {
            ollamaContainer.start();
            System.out.println(ollamaContainer.getEndpoint());
            languageModel = OllamaChatModel.builder()
                    .baseUrl("http://localhost:9090")
                    .modelName(MODEL_NAME)
                    .temperature(0.3)
                    .timeout(Duration.ofMinutes(5))
                    .build();
        }
        MusicianAssistant assistant = new MusicianAssistant();
        Musician musician = new MusicianAssistant().generateTopThreeAlbums( "Miles Davis",languageModel);
        assertTrue(musician.albums().contains("Kind of Blue"));
    }

    private OllamaContainer buildOllamaContainer() throws IOException, InterruptedException{
        List<Image> listImagesCmd = DockerClientFactory.lazyClient()
                .listImagesCmd()
                .withImageNameFilter(MODEL_NAME)
                .exec();
        if (listImagesCmd.isEmpty()) {
            System.out.println("Creating a new Ollama container with the model image...");
            OllamaContainer ollama = new OllamaContainer("ollama/ollama:latest");
            ollama.start();
            ollama.execInContainer("ollama", "pull", MODEL_NAME);
            ollama.commitToImage(MODEL_NAME);
            return ollama;
        } else {
            System.out.println("Using existing Ollama container with model image...");
            return new OllamaContainer(
                    DockerImageName.parse(MODEL_NAME)
                            .asCompatibleSubstituteFor("ollama/ollama"));
        }

    }
}
