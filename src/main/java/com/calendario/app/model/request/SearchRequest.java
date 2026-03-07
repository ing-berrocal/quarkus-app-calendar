package com.calendario.app.model.request;

public class SearchRequest {
    
    private Boolean phoneOrEmail;
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
