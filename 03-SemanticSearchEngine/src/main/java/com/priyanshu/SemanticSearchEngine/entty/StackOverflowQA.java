package com.priyanshu.SemanticSearchEngine.entty;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Data
@Table(name = "stack_overflowqa")
public class StackOverflowQA {

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    Long Id;

    @Column(nullable = false, columnDefinition = "TEXT")
    String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    String answer;

    @Column(nullable = false)
    Integer votes;

    @ElementCollection
    List<String> tags;

}
