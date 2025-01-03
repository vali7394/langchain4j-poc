package com.langchain4j.learning.assistant.embedding;


import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModel;
import dev.langchain4j.model.openai.OpenAiEmbeddingModelName;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.CosineSimilarity;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore;

import java.util.List;

public class EmbeddingAssistant {

    public static void main(String[] args) {

        EmbeddingStore<TextSegment> pgVectorEmbedStore = PgVectorEmbeddingStore
                .builder()
                .host("localhost")
                .port(5532)
                .createTable(true)
                .table("embeded_store_poc")
                .dimension(384)
                .user("ai")
                .password("ai")
                .database("langchain4j").build();
        cleanEmbeddingDb(pgVectorEmbedStore);
        pgVectorEmbeddingStore(pgVectorEmbedStore);
        queryEmbeddingStore(pgVectorEmbedStore,localEmbeddingModel(TextSegment.from("What is your favourite sport?")));
      //  cleanEmbeddingDb(pgVectorEmbedStore);
    }



    private static void queryEmbeddingStore(EmbeddingStore<TextSegment> embeddingStore, Embedding embedding){
        EmbeddingSearchRequest request = EmbeddingSearchRequest.builder()
                .queryEmbedding(embedding)
                .minScore(0.6)
                .maxResults(2)
                .build();
        List<EmbeddingMatch<TextSegment>> embeddingMatches = embeddingStore.search(request).matches();
        embeddingMatches
                .forEach(embeddingMatch ->{
                    System.out.println(embeddingMatch.score());
                    System.out.println(embeddingMatch.embedded().text());
                });
    }
    private static void pgVectorEmbeddingStore(EmbeddingStore<TextSegment> embeddingStore) {

        TextSegment textSegment2 = TextSegment.from( "The weather is good today.");
        TextSegment textSegment3 = TextSegment.from( "I like cricket.");
        TextSegment textSegment1 = TextSegment.from( "I like football.");
        TextSegment textSegment4 = TextSegment.from("Cricket is a religion in India");

        embeddingStore.add(localEmbeddingModel(textSegment1),textSegment1);
        embeddingStore.add(localEmbeddingModel(textSegment2),textSegment2);
        embeddingStore.add(localEmbeddingModel(textSegment3),textSegment3);

    }
    public static Embedding localEmbeddingModel(TextSegment textSegment){
        EmbeddingModel embeddingModel = new AllMiniLmL6V2EmbeddingModel();
        Response<Embedding> response = embeddingModel.embed(textSegment);
        return response.content();
    }

    private static void cleanEmbeddingDb(EmbeddingStore<TextSegment> embeddingStore){
        embeddingStore.removeAll();
    }

    private static void openAIEmbedding(){
        EmbeddingModel embeddingModel = OpenAiEmbeddingModel
                .builder()
                .modelName(OpenAiEmbeddingModelName.TEXT_EMBEDDING_ADA_002)
                //.logRequests(true)
                .apiKey("")
                .build();


        Response<Embedding> isaacEmbedding = embeddingModel.embed("Isaac Asimov");
        Response<Embedding> noraEmbedding = embeddingModel.embed("Nora jemisin");
        Response<Embedding> fenderGuitarEmbedding = embeddingModel.embed("Fender Guitar");
        Response<Embedding> tableEmbedding = embeddingModel.embed("Table");

        System.out.println(isaacEmbedding.content().vectorAsList().size());
        System.out.println(CosineSimilarity.between(isaacEmbedding.content(),noraEmbedding.content()));
        System.out.println(CosineSimilarity.between(isaacEmbedding.content(),fenderGuitarEmbedding.content()));
        System.out.println(CosineSimilarity.between(tableEmbedding.content(),isaacEmbedding.content()));
    }

}
