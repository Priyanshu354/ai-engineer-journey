package com.priyanshu.SemanticSearchEngine.repository;

import com.priyanshu.SemanticSearchEngine.entty.StackOverflowQA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepository extends JpaRepository<StackOverflowQA, Long> {
}
