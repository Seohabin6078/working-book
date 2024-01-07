package com.example.workingbook.word.service;

import com.example.workingbook.exception.BusinessLogicException;
import com.example.workingbook.exception.ExceptionCode;
import com.example.workingbook.word.entity.Word;
import com.example.workingbook.word.repository.WordRepository;
import com.example.workingbook.wordbook.entity.WordBook;
import com.example.workingbook.wordbook.service.WordBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WordService {
    private final WordRepository wordRepository;
    private final WordBookService wordBookService;

    public Word createWord(Word word) {
        return wordRepository.save(word);
    }

    public Word updateWord(Word word) {
        Word findWord = findWord(word.getWordId());

        Optional.ofNullable(word.getName())
                .ifPresent(findWord::changeName);
        Optional.ofNullable(word.getContent())
                .ifPresent(findWord::changeContent);

        return wordRepository.save(findWord);
    }

    public Word findWord(Long wordId) {
        return wordRepository.findById(wordId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.WORD_NOT_FOUND));
    }

    public List<Word> findWords(Long wordBookId) {
        WordBook findWordBook = wordBookService.findWordBook(wordBookId);
        return wordRepository.findByWordBook(findWordBook);
    }

    public void deleteWord(Long wordId) {
        Word findWord = findWord(wordId);
        wordRepository.delete(findWord);
    }

    // todo 쿼리 어떻게 날라가는지 꼭 확인하기
    public void deleteWords(Long wordBookId) {
        List<Word> words = findWords(wordBookId);
        wordRepository.deleteAll(words);
    }
}
