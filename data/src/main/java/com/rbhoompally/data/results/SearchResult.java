package com.rbhoompally.data.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rbhoompally.data.models.ShelfItem;

import java.util.List;

public class SearchResult {
    @JsonProperty("Search")
    public List<ShelfItem> searchItems;
}
