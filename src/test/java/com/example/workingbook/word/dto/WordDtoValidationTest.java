package com.example.workingbook.word.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class WordDtoValidationTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void Post_실패_테스트() {
        //given
        WordDto.Post post1 = WordDto.Post.builder()
                .name("")
                .content("")
                .build();

        WordDto.Post post2 = WordDto.Post.builder()
                .build();

        WordDto.Post post3 = WordDto.Post.builder()
                .name("이름")
                .content("  ")
                .build();

        WordDto.Post post4 = WordDto.Post.builder()
                .content("내용")
                .build();

//        WordDto.Post post3 = WordDto.Post.builder()
//                .name(" 단어")
//                .content("내용 ")
//                .build();
//
//        WordDto.Post post4 = WordDto.Post.builder()
//                .name("단 어")
//                .content("내  용")
//                .build();
//
//        WordDto.Post post5 = WordDto.Post.builder()
//                .name("1234")
//                .content("1234!")
//                .build();

        //when
        Set<ConstraintViolation<WordDto.Post>> validate1 = validator.validate(post1);
        Set<ConstraintViolation<WordDto.Post>> validate2 = validator.validate(post2);
        Set<ConstraintViolation<WordDto.Post>> validate3 = validator.validate(post3);
        Set<ConstraintViolation<WordDto.Post>> validate4 = validator.validate(post4);
//        Set<ConstraintViolation<WordDto.Post>> validate5 = validator.validate(post5);

        //then
        assertThat(validate1.size()).isEqualTo(2);
        assertThat(validate2.size()).isEqualTo(2); // null이 할당되면 정규표현식은 통과된다.
        assertThat(validate3.size()).isEqualTo(1);
        assertThat(validate4.size()).isEqualTo(1);
//        assertThat(validate5.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("모든 필드가 null인 경우")
    void Patch_성공_테스트() {
        //given
        WordDto.Patch patch = WordDto.Patch.builder().build();

        //when
        Set<ConstraintViolation<WordDto.Patch>> validate = validator.validate(patch);

        //then
        assertThat(validate.size()).isEqualTo(0);
    }
}
