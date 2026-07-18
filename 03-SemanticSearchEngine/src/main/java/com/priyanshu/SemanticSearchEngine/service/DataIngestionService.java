package com.priyanshu.SemanticSearchEngine.service;

import org.springframework.ai.vectorstore.VectorStore;
import com.priyanshu.SemanticSearchEngine.dto.DataFormat;
import com.priyanshu.SemanticSearchEngine.dto.DataIngestionRequest;
import com.priyanshu.SemanticSearchEngine.dto.DataIngestionResponse;
import com.priyanshu.SemanticSearchEngine.entty.StackOverflowQA;
import com.priyanshu.SemanticSearchEngine.repository.DataIngestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.document.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DataIngestionService {

    private final DataIngestionRepository dataIngestionRepository;
    List<StackOverflowQA> allData = new ArrayList<>();

    private  final VectorStore vectorStore;

    public DataIngestionResponse insertData(DataIngestionRequest request){
        for(DataFormat d : request.data()) {
            StackOverflowQA data = StackOverflowQA.builder()
                    .votes(d.votes())
                    .question(d.question())
                    .answer(d.answer())
                    .tags(d.tags())
                    .build();

            allData.add(data);
        }

        List<StackOverflowQA> saved = dataIngestionRepository.saveAll(allData);

        List<Document> documents = saved.stream()
                .map(qa -> new Document(
                        qa.getQuestion() + "\n\n" + qa.getAnswer(),
                        Map.of(
                                "qaId", qa.getId(),
                                "votes", qa.getVotes(),
                                "tags", String.join(",", qa.getTags())
                        )
                ))
                .toList();

        vectorStore.add(documents);

        return DataIngestionResponse.builder()
                .count(allData.size())
                .build();
    }


}
