package com.priyanshu.SemanticSearchEngine.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class DataIngestionResponse{
    int count;
    Double time;
}
