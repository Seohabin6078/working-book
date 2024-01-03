package com.example.workingbook.wordbook.service;

import com.example.workingbook.exception.BusinessLogicException;
import com.example.workingbook.exception.ExceptionCode;
import com.example.workingbook.wordbook.entity.WordBook;
import com.example.workingbook.wordbook.repository.WordBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WordBookService {
    private final WordBookRepository wordBookRepository;

    public WordBook createWordBook(WordBook wordBook) {
        return wordBookRepository.save(wordBook);
    }

    public WordBook updateWordBook(WordBook wordBook) {
        WordBook findWordBook = findWordBook(wordBook.getWordBookId());

        Optional.ofNullable(wordBook.getTitle())
                .ifPresent(findWordBook::changeTitle);
        Optional.ofNullable(wordBook.getAccessRange())
                .ifPresent(findWordBook::changeAccessRange);

        return wordBookRepository.save(findWordBook);
    }

    public WordBook findWordBook(Long wordBookId) {
        return wordBookRepository.findById(wordBookId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WORD_BOOK_NOT_FOUND));
    }

    public List<WordBook> findWordBooks() {
        return wordBookRepository.findAll();
    }

    public void deleteWordBook(Long wordBookId) {
        WordBook findWordBook = findWordBook(wordBookId);
        wordBookRepository.delete(findWordBook);
    }
}
