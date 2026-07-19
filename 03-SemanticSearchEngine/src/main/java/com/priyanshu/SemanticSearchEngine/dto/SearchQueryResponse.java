package com.priyanshu.SemanticSearchEngine.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record SearchQueryResponse(
        String query,
        List<QueryResult> results,
        Integer totalFound,
        Long searchTimeInMs
) {

}
