package com.priyanshu.Java_Code_Review_Assistant.service;

import com.priyanshu.Java_Code_Review_Assistant.llmSystemPrompt.PromptUtil;
import com.priyanshu.Java_Code_Review_Assistant.model.CodeReviewResult;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class AiCodeReviewService {

    private final ChatClient chatClient;

    public AiCodeReviewService(ChatModel chatModel) {
        this.chatClient = ChatClient.builder(chatModel).build();
    }

    public CodeReviewResult codeReview(String userCode) {
        String finalPrompt = PromptUtil.SYSTEM_PROMPT.replace("{{CODE}}", userCode);

        return chatClient.prompt()
                .user(finalPrompt)
                .call()
                .entity(CodeReviewResult.class);
    }
}