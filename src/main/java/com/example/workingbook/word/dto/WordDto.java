package com.example.workingbook.word.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class WordDto {
    @Builder
    @Getter
    public static class Post {
        @NotBlank
//        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+(?:\\s[a-zA-Z0-9가-힣]+)*$",
//        message = "영문자, 숫자, 한글만 허용하며 공백은 문자 사이에 연속될 수 없고 시작과 끝에 올 수 없습니다.")
        private String name;

        @NotBlank
//        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+(?:\\s[a-zA-Z0-9가-힣]+)*$",
//                message = "영문자, 숫자, 한글만 허용하며 공백은 문자 사이에 연속될 수 없고 시작과 끝에 올 수 없습니다.")
        private String content;
    }

    @Builder
    @Getter
    public static class Patch {
        private Long wordId;

//        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+(?:\\s[a-zA-Z0-9가-힣]+)*$",
//                message = "영문자, 숫자, 한글만 허용하며 공백은 문자 사이에 연속될 수 없고 시작과 끝에 올 수 없습니다.")
        private String name;

//        @Pattern(regexp = "^[a-zA-Z0-9가-힣]+(?:\\s[a-zA-Z0-9가-힣]+)*$",
//                message = "영문자, 숫자, 한글만 허용하며 공백은 문자 사이에 연속될 수 없고 시작과 끝에 올 수 없습니다.")
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
