package com.priyanshu.SemanticSearchEngine.controller;

import com.priyanshu.SemanticSearchEngine.dto.DataIngestionRequest;
import com.priyanshu.SemanticSearchEngine.dto.DataIngestionResponse;
import com.priyanshu.SemanticSearchEngine.repository.DataIngestionRepository;
import com.priyanshu.SemanticSearchEngine.service.DataIngestionService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CurrentTimestamp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class DataIngestionController {

    private final DataIngestionService dataIngestionService;

    @PostMapping("/api/data/ingestion")
    ResponseEntity<DataIngestionResponse> ingestionData(@RequestBody DataIngestionRequest request) {
        long startTime = System.currentTimeMillis();

        DataIngestionResponse response = dataIngestionService.insertData(request);

        long endTime = System.currentTimeMillis();

        double timeTaken = (endTime - startTime) / 1000.0;
        response.setTime(timeTaken);

        return ResponseEntity.ok(response);
    }

}
