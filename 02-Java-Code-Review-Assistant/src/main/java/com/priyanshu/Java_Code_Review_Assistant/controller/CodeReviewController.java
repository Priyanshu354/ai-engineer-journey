package com.priyanshu.Java_Code_Review_Assistant.controller;

import com.priyanshu.Java_Code_Review_Assistant.CodeDto;
import com.priyanshu.Java_Code_Review_Assistant.model.CodeReviewResult;
import com.priyanshu.Java_Code_Review_Assistant.service.AiCodeReviewService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/api/code-review")
public class CodeReviewController {

    AiCodeReviewService aiCodeReviewService;


    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE)
    public CodeReviewResult codeReview(@RequestBody String code) {
        return aiCodeReviewService.codeReview(code);
    }

}
