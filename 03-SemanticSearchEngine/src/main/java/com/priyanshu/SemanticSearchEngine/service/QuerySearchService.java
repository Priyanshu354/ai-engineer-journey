package com.priyanshu.SemanticSearchEngine.service;

import com.priyanshu.SemanticSearchEngine.dto.QueryResult;
import com.priyanshu.SemanticSearchEngine.dto.SearchQueryResponse;
import com.priyanshu.SemanticSearchEngine.dto.SearchQueryRequest;
import com.priyanshu.SemanticSearchEngine.entty.StackOverflowQA;
import com.priyanshu.SemanticSearchEngine.repository.DataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuerySearchService {

    private final VectorStore vectorStore;
    private final DataRepository dataRepository;

    public List<QueryResult> searchQuery(SearchQueryRequest request) {
        SearchRequest searchRequest = SearchRequest.builder()
                .query(request.query())
                .topK(request.topK())
                .similarityThreshold(request.threshold())
                //..filterExpression("tag == 'spring'") we can filter basics of tags
                .build();

        List<Document> documents = vectorStore.similaritySearch(searchRequest);

        List<QueryResult> results = new ArrayList<>();

        for(Document document : documents) {
            StackOverflowQA stackOverflowQA = dataRepository
                    .findById(((Number) document.getMetadata().get("qaId")).longValue())
                    .orElseThrow(() -> new RuntimeException("QA not found"));

            QueryResult result = QueryResult.builder()
                    .question(stackOverflowQA.getQuestion())
                    .answer(stackOverflowQA.getAnswer())
                    .votes(stackOverflowQA.getVotes())
                    .tags(stackOverflowQA.getTags())
                    .similarity(document.getScore())
                    .build();

            results.add(result);
        }
        return results;
    }



}
