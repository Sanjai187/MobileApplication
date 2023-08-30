package com.example.todo.model;

public class UserProfile {

    private String name;
    private String title;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public StringBuilder getProfileIconText() {
        final String[] words = name.split(" ");
        final StringBuilder text = new StringBuilder();

        for (final String word : words) {
            if (! word.isEmpty()) {
                text.append(Character.toUpperCase(word.charAt(0)));
            }
        }

        return text;
    }
}
