package com.mystiquex.ruvy.sql_transformer.models;



import java.util.Map;


public class DialogflowRequest {
    private QueryResult queryResult;
    private String session;

    // Getters and Setters

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public String getSession() {
        return session;
    }

    public void setQueryResult(QueryResult queryResult) {
        this.queryResult = queryResult;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public static class QueryResult {
        public String getQueryText() {
            return queryText;
        }

        public void setQueryText(String queryText) {
            this.queryText = queryText;
        }

        public Map<String, Object> getParameters() {
            return parameters;
        }

        public void setParameters(Map<String, Object> parameters) {
            this.parameters = parameters;
        }

        public Intent getIntent() {
            return intent;
        }

        public void setIntent(Intent intent) {
            this.intent = intent;
        }

        private String queryText;
        private Map<String, Object> parameters;
        private Intent intent;

        // Getters and Setters

        public static class Intent {
            public String getDisplayName() {
                return displayName;
            }

            public void setDisplayName(String displayName) {
                this.displayName = displayName;
            }

            private String displayName;

            // Getters and Setters
        }
    }
}

