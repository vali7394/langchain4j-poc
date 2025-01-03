package com.langchain4j.learning.assistant.extending;

import dev.langchain4j.agent.tool.Tool;

import java.time.LocalDate;

public class LegalDocumentsTools {

    @Tool("Returns the last time the PRIVACY document was updated")
    public LocalDate lastUpdatePrivacy() {
        return LocalDate.of(2013, 3, 9);
    }

    @Tool("Returns the last time the TERMS AND CONDITIONS document was updated")
    public LocalDate lastUpdateTerms() {
        return LocalDate.of(2014, 6, 19);
    }

    @Tool("Returns the last time the END USER LICENSE AGREEMENT document was updated")
    public LocalDate lastEndUserLicenseAgreement() {
        return LocalDate.of(2021, 11, 23);
    }

}
