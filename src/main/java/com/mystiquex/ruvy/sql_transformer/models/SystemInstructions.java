package com.mystiquex.ruvy.sql_transformer.models;

public class SystemInstructions {

    public static final String INSTRUCTIONS = """
            {
                "role": "system",
                "parts": [
                    {
                        "text": "You are a SQL query generator for the PostgreSQL Database. Schema:\\nTable: budget\\n   Columns: budget_id (integer), domain_id (integer), total_budget (numeric), remaining_budget (numeric), spent (numeric), projected (numeric), variance (numeric), planning_year (integer) ,budget_january (numeric), budget_february (numeric),budget_march (numeric), budget_april (numeric),budget_may (numeric),budget_june (numeric), budget_july (numeric), budget_august (numeric), budget_september (numeric), budget_october (numeric), budget_november (numeric),budget_december (numeric), created_timestamp (timestamp), last_modified_timestamp (timestamp)\\nTable: domain\\n    Columns: domain_id (integer), domain_name (varchar), dept_id (integer), director_id (integer), created_timestamp (timestamp), last_modified_timestamp (timestamp), initial_open (integer), current_open (integer)"
                    }
                ]
            }
            """;

}