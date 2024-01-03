package com.example.workingbook.word.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class WordDto {
    @Builder
    @Getter
    public static class Post {
        @NotBlank
        private String name;

        @NotBlank
        private String content;
    }

    @Builder
    @Getter
    public static class Patch {
        private Long wordId;
        private String name;
        private String content;

        public void setWordId(Long wordId) {
            this.wordId = wordId;
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long wordId;
        private String name;
        private String content;
        private Long wordBookId;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
