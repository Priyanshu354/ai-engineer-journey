package com.priyanshu.Java_Code_Review_Assistant.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.priyanshu.Java_Code_Review_Assistant.llmSystemPrompt.PromptUtil;
import com.priyanshu.Java_Code_Review_Assistant.model.CodeReviewResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Slf4j
public class AiCodeReviewService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AiCodeReviewService(ChatModel chatModel, ObjectMapper objectMapper) {
        this.chatClient = ChatClient.builder(chatModel).build();
        this.objectMapper = objectMapper;
    }

    public CodeReviewResult codeReview(String userCode) {
        String finalPrompt = PromptUtil.SYSTEM_PROMPT.replace("{{CODE}}", userCode);

        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                return chatClient.prompt()
                        .user(finalPrompt)
                        .call()
                        .entity(CodeReviewResult.class);
            } catch (Exception ex) {
                log.warn("Attempt {} failed: {}", attempt, ex.getMessage());
                if (attempt == 3) throw new RuntimeException("LLM call failed after 3 attempts", ex);
            }
        }
        throw new RuntimeException("Unreachable");
    }
}