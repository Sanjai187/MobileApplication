package com.example.todo.model;

import androidx.annotation.NonNull;

public class Todo {

    private Long id;
    private String label;
    private boolean isChecked;

    public Todo(final String label) {
        this.label = label;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked() {
        this.isChecked =! this.isChecked;
    }

    @NonNull
    public String toString() {
        return label;
    }
}