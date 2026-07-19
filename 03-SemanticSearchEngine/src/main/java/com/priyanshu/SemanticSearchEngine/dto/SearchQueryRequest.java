package com.priyanshu.SemanticSearchEngine.dto;


public record SearchQueryRequest (
        String query,
        Integer topK,
        Double threshold
        ) {
}
