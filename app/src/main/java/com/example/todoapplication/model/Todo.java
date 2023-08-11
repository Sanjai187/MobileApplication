package com.example.todoapplication.model;

import java.util.UUID;

/**
 * <p>
 * Representing the Todo pojo of the Todo application
 * </p>
 *
 * @author sanjai
 * @version 1.0
 */
public class Todo {

    private String id;
    private String label;
    private boolean isChecked;

    public Todo() {}

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

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked() {
        this.isChecked =! this.isChecked;
    }

    public String toString() {
        return label;
    }
}