package com.example.workingbook.word.mapper;

import com.example.workingbook.word.dto.WordDto;
import com.example.workingbook.word.entity.Word;
import com.example.workingbook.wordbook.entity.WordBook;
import com.example.workingbook.wordbook.service.WordBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class WordMapper { // 여기에서 WordBook 세팅을 하는 것이 맞는 것 같다.
    private final WordBookService wordBookService;

    public Word wordPostDtoToWord(WordDto.Post post, Long wordBookId) {
        WordBook wordBook = wordBookService.findWordBook(wordBookId);

        return Word.builder()
                .name(post.getName())
                .content(post.getContent())
                .wordBook(wordBook)
                .build();
    }

    public Word wordPatchDtoToWord(WordDto.Patch patch) {
        return Word.builder()
                .wordId(patch.getWordId())
                .name(patch.getName())
                .content(patch.getContent())
                .build();
    }

    public WordDto.Response wordToWordResponseDto(Word word) {
        return WordDto.Response.builder()
                .wordId(word.getWordId())
                .name(word.getName())
                .content(word.getContent())
                .wordBookId(word.getWordBook().getWordBookId())
                .createdAt(word.getCreatedAt())
                .modifiedAt(word.getModifiedAt())
                .build();
    }

    public List<WordDto.Response> wordsToWordResponseDtos(List<Word> words) {
        List<WordDto.Response> responses = new ArrayList<>();

        for (Word word : words) {
            WordDto.Response response = wordToWordResponseDto(word);
            responses.add(response);
        }

        return responses;
    }
}
