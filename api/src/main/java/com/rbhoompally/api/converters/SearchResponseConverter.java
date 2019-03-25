package com.rbhoompally.api.converters;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rbhoompally.data.results.SearchResult;

import java.io.IOException;

public class SearchResponseConverter {
    private static ObjectMapper mapper;
    private static SearchResponseConverter searchResponseConverter;

    private SearchResponseConverter() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static SearchResult convert(String response) throws IOException {
        if (searchResponseConverter == null) {
            searchResponseConverter = new SearchResponseConverter();
        }

        return mapper.readValue(response, SearchResult.class);
    }
}
