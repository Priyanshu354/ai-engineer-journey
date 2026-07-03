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

//    public CodeReviewResult codeReview(String userCode) {
//        String finalPrompt = PromptUtil.SYSTEM_PROMPT.replace("{{CODE}}", userCode);
//
//        for (int attempt = 1; attempt <= 3; attempt++) {
//            try {
//                return chatClient.prompt()
//                        .user(finalPrompt)
//                        .call()
//                        .entity(CodeReviewResult.class);
//            } catch (Exception ex) {
//                log.warn("Attempt {} failed: {}", attempt, ex.getMessage());
//                if (attempt == 3) throw new RuntimeException("LLM call failed after 3 attempts", ex);
//            }
//        }
//        throw new RuntimeException("Unreachable");
//    }

    public CodeReviewResult codeReview(String userCode) {

        String originalPrompt = PromptUtil.SYSTEM_PROMPT.replace("{{CODE}}", userCode);

        String prompt = originalPrompt;
        String previousResponse = null;

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {

                previousResponse = chatClient.prompt()
                        .user(prompt)
                        .call()
                        .content();

                log.info("LLM Response (Attempt {}):\n{}", attempt, previousResponse);

                // Clean markdown/code fences if present
                String cleanedResponse = cleanResponse(previousResponse);

                return objectMapper.readValue(cleanedResponse, CodeReviewResult.class);

            } catch (Exception e) {

                log.warn("Attempt {} failed: {}", attempt, e.getMessage());

                if (attempt == 3) {
                    throw new RuntimeException("LLM call failed after 3 attempts", e);
                }

                prompt = """
                    Your previous response could not be parsed.

                    Original Request:

                    %s

                    Previous Response:

                    %s

                    Parser Error:

                    %s

                    Please regenerate the ENTIRE response.

                    IMPORTANT:

                    - Return ONLY one valid JSON object.
                    - Do NOT use Markdown.
                    - Do NOT wrap the JSON inside ``` or ```json.
                    - Do NOT add explanations.
                    - Every field defined in the schema must exist.
                    - The response must be directly parsable using Jackson ObjectMapper.
                    """
                        .formatted(
                                originalPrompt,
                                previousResponse == null ? "" : previousResponse,
                                e.getMessage()
                        );
            }
        }

        throw new RuntimeException("Unreachable");
    }

    private String cleanResponse(String response) {

        if (response == null) {
            return "";
        }

        response = response.trim();

        // Remove ```json
        if (response.startsWith("```json")) {
            response = response.substring(7);
        }

        // Remove ```
        if (response.startsWith("```")) {
            response = response.substring(3);
        }

        if (response.endsWith("```")) {
            response = response.substring(0, response.length() - 3);
        }

        return response.trim();
    }
}