package com.priyanshu.SemanticSearchEngine.controller;

import com.priyanshu.SemanticSearchEngine.dto.QueryResult;
import com.priyanshu.SemanticSearchEngine.dto.SearchQueryResponse;
import com.priyanshu.SemanticSearchEngine.dto.SearchQueryRequest;
import com.priyanshu.SemanticSearchEngine.service.QuerySearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class DataSearchController {

    private final QuerySearchService querySearchService;
    @PostMapping("/search/query")
    ResponseEntity<SearchQueryResponse> search(@RequestBody SearchQueryRequest request) {
        Long start = System.currentTimeMillis();
        List<QueryResult> resultList = querySearchService.searchQuery(request);
        Long end = System.currentTimeMillis();
        Long time = end - start;
        SearchQueryResponse response = SearchQueryResponse.builder()
                .query(request.query())
                .results(resultList)
                .totalFound(resultList.size())
                .searchTimeInMs(time)
                .build();

        return ResponseEntity.ok(response);
    }
}
