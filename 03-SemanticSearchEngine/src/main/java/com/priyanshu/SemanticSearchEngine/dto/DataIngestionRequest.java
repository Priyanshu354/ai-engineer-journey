package com.priyanshu.SemanticSearchEngine.dto;

import java.util.List;

public record DataIngestionRequest(
        List<DataFormat> data
) {
}
