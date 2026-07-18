package com.priyanshu.SemanticSearchEngine.dto;

import java.util.List;

public record DataFormat(
        String question,
        String answer,
        Integer votes,
        List<String> tags
) {
}
