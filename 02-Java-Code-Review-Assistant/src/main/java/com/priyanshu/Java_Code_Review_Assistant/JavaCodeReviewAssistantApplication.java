package com.priyanshu.Java_Code_Review_Assistant;

import com.priyanshu.Java_Code_Review_Assistant.service.AiCodeReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class JavaCodeReviewAssistantApplication{

    private final AiCodeReviewService aiChatService;

    public static void main(String[] args) {
        SpringApplication.run(JavaCodeReviewAssistantApplication.class, args);
    }

//    @Override
//    public void run(String... args) {
//        System.out.println("Hi");
//        System.out.println(aiChatService.codeReview().getCodePoint());
//    }
}