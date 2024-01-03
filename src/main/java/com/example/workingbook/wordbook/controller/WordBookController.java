package com.example.workingbook.wordbook.controller;

import com.example.workingbook.wordbook.dto.WordBookDto;
import com.example.workingbook.wordbook.entity.WordBook;
import com.example.workingbook.wordbook.mapper.WordBookMapper;
import com.example.workingbook.wordbook.service.WordBookService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RequestMapping("/wordbooks")
@RestController
public class WordBookController {
    private final WordBookService wordBookService;
    private final WordBookMapper mapper;

    @PostMapping
    public ResponseEntity<WordBookDto.Response> postWordBook(@RequestBody @Valid WordBookDto.Post requestBody) {
        WordBook wordBook = wordBookService.createWordBook(mapper.wordBookPostDtoToWordBook(requestBody));
        WordBookDto.Response response = mapper.wordBookToWordBookResponseDto(wordBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/{wordbook-id}")
    public ResponseEntity<WordBookDto.Response> patchWordBook(@RequestBody @Valid WordBookDto.Patch requestBody,
                                                              @PathVariable("wordbook-id") @Positive Long wordBookId) {
        requestBody.setWordBookId(wordBookId);
        WordBook wordBook = wordBookService.updateWordBook(mapper.wordBookPatchDtoToWordBook(requestBody));
        WordBookDto.Response response = mapper.wordBookToWordBookResponseDto(wordBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{wordbook-id}")
    public ResponseEntity<WordBookDto.Response> getWordBook(@PathVariable("wordbook-id") @Positive Long wordBookId) {
        WordBook wordBook = wordBookService.findWordBook(wordBookId);
        WordBookDto.Response response = mapper.wordBookToWordBookResponseDto(wordBook);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{wordbook-id}")
    public ResponseEntity<Void> deleteWordBook(@PathVariable("wordbook-id") @Positive Long wordBookId) {
        wordBookService.deleteWordBook(wordBookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
