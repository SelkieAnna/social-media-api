package com.selkieanna.socialmediaapi.posting.controller;

public class PostingRequest {
    private String header;
    private String text;

    public PostingRequest(String header, String text) {
        this.header = header;
        this.text = text;
    }

    public String getHeader() {
        return header;
    }

    public String getText() {
        return text;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setText(String text) {
        this.text = text;
    }
}
