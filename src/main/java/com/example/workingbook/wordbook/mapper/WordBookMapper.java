package com.example.workingbook.wordbook.mapper;

import com.example.workingbook.wordbook.dto.WordBookDto;
import com.example.workingbook.wordbook.entity.WordBook;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WordBookMapper {
    public WordBook wordBookPostDtoToWordBook(WordBookDto.Post post) {
        return WordBook.builder()
                .title(post.getTitle())
                .accessRange(post.getAccessRange())
                .build();
    }

    public WordBook wordBookPatchDtoToWordBook(WordBookDto.Patch patch) {
        return WordBook.builder()
                .wordBookId(patch.getWordBookId())
                .title(patch.getTitle())
                .accessRange(patch.getAccessRange())
                .build();
    }

    public WordBookDto.Response wordBookToWordBookResponseDto(WordBook wordBook) {
        return WordBookDto.Response.builder()
                .wordBookId(wordBook.getWordBookId())
                .title(wordBook.getTitle())
                .accessRange(wordBook.getAccessRange())
                .createdAt(wordBook.getCreatedAt())
                .modifiedAt(wordBook.getModifiedAt())
                .build();
    }

    public List<WordBookDto.Response> wordBooksToWordBookResponseDtos(List<WordBook> wordBooks) {
        List<WordBookDto.Response> responses = new ArrayList<>();

        for (WordBook wordBook : wordBooks) {
            WordBookDto.Response response = wordBookToWordBookResponseDto(wordBook);
            responses.add(response);
        }

        return responses;
    }
}
