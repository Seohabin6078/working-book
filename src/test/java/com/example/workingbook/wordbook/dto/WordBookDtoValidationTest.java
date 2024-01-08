package com.example.workingbook.wordbook.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.*;

public class WordBookDtoValidationTest {
    private static Validator validator;

    @BeforeAll
    static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void Post_실패_테스트() {
        //given
        WordBookDto.Post post1 = WordBookDto.Post.builder()
                .accessRange(0)
                .build();

        WordBookDto.Post post2 = WordBookDto.Post.builder()
                .title("title")
                .build();

        WordBookDto.Post post3 = WordBookDto.Post.builder()
                .title("title")
                .accessRange(3)
                .build();

        WordBookDto.Post post4 = WordBookDto.Post.builder()
                .build();

        WordBookDto.Post post5 = WordBookDto.Post.builder()
                .title(null)
                .accessRange(0)
                .build();

        //when
        Set<ConstraintViolation<WordBookDto.Post>> validate1 = validator.validate(post1);
        Set<ConstraintViolation<WordBookDto.Post>> validate2 = validator.validate(post2);
        Set<ConstraintViolation<WordBookDto.Post>> validate3 = validator.validate(post3);
        Set<ConstraintViolation<WordBookDto.Post>> validate4 = validator.validate(post4);
        Set<ConstraintViolation<WordBookDto.Post>> validate5 = validator.validate(post5);

        //then
        assertThat(validate1.size()).isEqualTo(1);
        assertThat(validate2.size()).isEqualTo(1);
        assertThat(validate3.size()).isEqualTo(1);
        assertThat(validate4.size()).isEqualTo(2);
        assertThat(validate5.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("모든 필드가 null이여도 통과하는가")
    void Patch_성공_테스트() {
        //given
        WordBookDto.Patch patch = WordBookDto.Patch.builder().build();

        //when
        Set<ConstraintViolation<WordBookDto.Patch>> validate = validator.validate(patch);

        //then
        assertThat(validate.size()).isEqualTo(0);
    }

    @Test
    void Patch_실패_테스트() {
        //given
        WordBookDto.Patch patch = WordBookDto.Patch.builder()
                .accessRange(5)
                .build();

        //when
        Set<ConstraintViolation<WordBookDto.Patch>> validate = validator.validate(patch);

        //then
        assertThat(validate.size()).isEqualTo(1);
    }
}
