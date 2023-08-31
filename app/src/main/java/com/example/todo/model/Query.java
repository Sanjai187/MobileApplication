package com.example.todo.model;

import java.util.List;

public class Quary {

    private String search;
    private List<String> searchAttribute;
    private Filter filter;

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public List<String> getSearchAttribute() {
        return searchAttribute;
    }

    public void setSearchAttribute(List<String> searchAttribute) {
        this.searchAttribute = searchAttribute;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
