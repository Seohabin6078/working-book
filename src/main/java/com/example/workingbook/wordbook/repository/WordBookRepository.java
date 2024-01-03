package com.example.workingbook.wordbook.repository;

import com.example.workingbook.wordbook.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordBookRepository extends JpaRepository<WordBook, Long> {
}
