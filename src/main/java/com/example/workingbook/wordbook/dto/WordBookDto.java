package com.example.workingbook.wordbook.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class WordBookDto {
    @Builder
    @Getter
    public static class Post {
        @NotBlank
        private String title;

        @NotNull
        @Min(0)
        @Max(2)
        private Integer accessRange;
    }

    @Builder
    @Getter
    public static class Patch {
        private Long wordBookId;
        private String title;

        @Min(0)
        @Max(2)
        private Integer accessRange;

        public void setWordBookId(Long wordBookId) {
            this.wordBookId = wordBookId;
        }
    }

    @Builder
    @Getter
    public static class Response {
        private Long wordBookId;
        private String title;
        private Integer accessRange;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
