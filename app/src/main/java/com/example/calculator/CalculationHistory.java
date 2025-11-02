package com.example.calculator;

public class CalculationHistory {
    private String input;
    private String result;
    private String type;
    private String timestamp;

    public CalculationHistory(String input, String result, String type) {
        this.input = input;
        this.result = result;
        this.type = type;
    }

    // Getters and setters
    public String getInput() { return input; }
    public void setInput(String input) { this.input = input; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
}