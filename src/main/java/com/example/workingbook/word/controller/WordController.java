package com.example.workingbook.word.controller;

import com.example.workingbook.word.dto.WordDto;
import com.example.workingbook.word.entity.Word;
import com.example.workingbook.word.mapper.WordMapper;
import com.example.workingbook.word.service.WordService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RequestMapping("/wordbooks")
@RestController
public class WordController {
    private final WordService wordService;
    private final WordMapper mapper;

    @PostMapping("/{wordbook-id}/words")
    public ResponseEntity<WordDto.Response> postWord(@RequestBody @Valid WordDto.Post requestBody,
                                                     @PathVariable("wordbook-id") @Positive Long wordBookId) {
        Word word = wordService.createWord(mapper.wordPostDtoToWord(requestBody, wordBookId));
        WordDto.Response response = mapper.wordToWordResponseDto(word);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{wordbook-id}/words/{word-id}")
    public ResponseEntity<WordDto.Response> patchWord(@RequestBody @Valid WordDto.Patch requestBody,
                                                      @PathVariable("word-id") @Positive Long wordId) {
        requestBody.setWordId(wordId);
        Word word = wordService.updateWord(mapper.wordPatchDtoToWord(requestBody));
        WordDto.Response response = mapper.wordToWordResponseDto(word);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{wordbook-id}/words")
    public ResponseEntity<List<WordDto.Response>> getWords(@PathVariable("wordbook-id") @Positive Long wordBookId) {
        List<Word> words = wordService.findWords(wordBookId);
        List<WordDto.Response> responses = mapper.wordsToWordResponseDtos(words);
        return new ResponseEntity<>(responses, HttpStatus.OK);
    }

    @GetMapping("/{wordbook-id}/words/{word-id}")
    public ResponseEntity<WordDto.Response> getWord(@PathVariable("word-id") @Positive Long wordId) {
        Word word = wordService.findWord(wordId);
        WordDto.Response response = mapper.wordToWordResponseDto(word);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{wordbook-id}/words/{word-id}")
    public ResponseEntity<Void> deleteWord(@PathVariable("word-id") @Positive Long wordId) {
        wordService.deleteWord(wordId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{wordbook-id}/words")
    public ResponseEntity<Void> deleteWords(@PathVariable("wordbook-id") @Positive Long wordBookId) {
        wordService.deleteWords(wordBookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
