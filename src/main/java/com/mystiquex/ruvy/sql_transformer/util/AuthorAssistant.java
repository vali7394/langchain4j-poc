package com.mystiquex.ruvy.sql_transformer.util;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface AuthorAssistant {
    @SystemMessage("You are an expert in Science Fiction Books")
    @UserMessage("Write a short Bio of author {{author}}")
    String getAuthorBio(@V("author") String authorName);

}
