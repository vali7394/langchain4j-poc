package com.mystiquex.ruvy.sql_transformer.models;




public class DialogflowResponse {
    private String fulfillmentText;

    public DialogflowResponse(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }

    public String getFulfillmentText() {
        return fulfillmentText;
    }

    public void setFulfillmentText(String fulfillmentText) {
        this.fulfillmentText = fulfillmentText;
    }
// Getter and Setter
}
