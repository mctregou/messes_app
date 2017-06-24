package com.tregouet.messesapp.modules.search;

import com.tregouet.messesapp.model.SearchResult;

import java.util.ArrayList;

/**
 * Created by mariececile.tregouet on 24/06/2017.
 */
public class SearchEvent {

    private ArrayList<SearchResult> results = new ArrayList<>();

    public SearchEvent(ArrayList<SearchResult> results) {
        this.results = results;
    }

    public ArrayList<SearchResult> getResults() {
        return results;
    }

    public void setResults(ArrayList<SearchResult> results) {
        this.results = results;
    }
}
