package com.example.todoapplication.model;

import java.util.UUID;

public class Todo {

    private String id;
    private String label;
    private boolean isChecked;

    public Todo() {

    }
    public Todo(String label) {
        this.label = label;
        this.isChecked = false;
        this.id = UUID.randomUUID().toString();
    }
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setIsChecked(final boolean isChecked) {
        this.isChecked = isChecked;
    }

    public String toString() {
        return label;
    }
}