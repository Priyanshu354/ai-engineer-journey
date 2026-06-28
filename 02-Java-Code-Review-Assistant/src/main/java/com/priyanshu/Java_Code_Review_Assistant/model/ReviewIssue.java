package com.priyanshu.Java_Code_Review_Assistant.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReviewIssue {
    String title;
    String description;
    int lineNo;
    String error;
    String fix;
}
