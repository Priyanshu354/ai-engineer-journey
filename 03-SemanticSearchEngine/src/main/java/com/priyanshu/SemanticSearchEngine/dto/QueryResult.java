package com.priyanshu.SemanticSearchEngine.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record QueryResult(
        String question,
        String answer,
        List<String> tags,
        Integer votes,
        Double similarity
) {
}
