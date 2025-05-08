package com.handyHive23.handyHive23.service;

import jakarta.persistence.Embeddable;

@Embeddable
public class CommentEntry {

    private String username;
    private String text;
    private String date;

    public CommentEntry() {
    }

    public CommentEntry(String username, String text, String date) {
        this.username = username;
        this.text = text;
        this.date = date;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    // Setters
    public void setUsername(String username) {
        this.username = username;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
