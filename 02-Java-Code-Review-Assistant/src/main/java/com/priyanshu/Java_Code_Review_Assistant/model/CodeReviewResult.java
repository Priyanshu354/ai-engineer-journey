package com.priyanshu.Java_Code_Review_Assistant.model;

import com.priyanshu.Java_Code_Review_Assistant.CodeQuality;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CodeReviewResult {
    int rating;
    CodeQuality codeQuality;
    String timeComplexity;
    String spaceComplexity;
    List<ReviewIssue> bugs;
    List<ReviewIssue> improvement;
    String fullyCorrectedCode;
    String CodeSummary;
    private List<String> bestPracticesViolated;
}
