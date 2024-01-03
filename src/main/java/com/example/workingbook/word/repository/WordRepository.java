package com.example.workingbook.word.repository;

import com.example.workingbook.word.entity.Word;
import com.example.workingbook.wordbook.entity.WordBook;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByWordBook(WordBook wordBook);
}
