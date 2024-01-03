package com.example.workingbook.wordbook.dto;

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

        String expectMessage1 = "공백일 수 없습니다";
        String expectMessage2 = "널이어서는 안됩니다";
        String expectMessage3 = "2 이하여야 합니다";

        //when
        Set<ConstraintViolation<WordBookDto.Post>> validate1 = validator.validate(post1);
        Set<ConstraintViolation<WordBookDto.Post>> validate2 = validator.validate(post2);
        Set<ConstraintViolation<WordBookDto.Post>> validate3 = validator.validate(post3);

        //then
        assertThat(validate1.iterator().next().getMessage()).isEqualTo(expectMessage1);
        assertThat(validate2.iterator().next().getMessage()).isEqualTo(expectMessage2);
        assertThat(validate3.iterator().next().getMessage()).isEqualTo(expectMessage3);
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
        String expectMessage = "2 이하여야 합니다";

        //when
        Set<ConstraintViolation<WordBookDto.Patch>> validate = validator.validate(patch);

        //then
        assertThat(validate.iterator().next().getMessage()).isEqualTo(expectMessage);
    }
}
