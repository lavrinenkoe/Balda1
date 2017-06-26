package com.example.lavr.balda1.components;

/**
 * Created by Lavr on 06.06.2017.
 */

public class SearchResult {
    public final String name;
    public final int index;
    public final boolean isFound;

    public SearchResult(String name, int index, boolean isFound) {
        this.name = name;
        this.isFound = isFound;
        this.index = index;
    }
}
