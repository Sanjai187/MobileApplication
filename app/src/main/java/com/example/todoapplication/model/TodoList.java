package com.example.todoapplication.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TodoList {

    private List<Todo> todoList;
    private String parentId;

    public TodoList(String parentId) {
        this.parentId = parentId;
        this.todoList = new ArrayList<>();
    }
    public String getParentId() {
        return parentId;
    }

    public void setParentId(final String parentId) {
        this.parentId = parentId;
    }

    public void add(final Todo todo) {
        todoList.add(todo);
    }

    public void remove(final String id) {
        todoList = todoList.stream().filter(todo -> !Objects.equals(todo.getId(), id)).collect(Collectors.toList());
    }

    public List<Todo> getAll() {
        return todoList;
    }
    public List getAllList(final Long parentId) {
        if (parentId == null) {
            return todoList;
        }

        return  todoList.stream().filter(todo -> getParentId().equals(parentId)).collect(Collectors.toList());
    }
}
